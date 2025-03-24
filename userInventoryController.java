package library.libraryapp.controller;

import library.libraryapp.model.userInventoryItem;
import library.libraryapp.model.userTransaction;
import library.libraryapp.service.UserInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Controller
public class userInventoryController {

    private final UserInventoryService inventoryService;

    @Autowired
    public userInventoryController(UserInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/userInventory")
    public String getAllInventoryItems(@RequestParam(value = "searchQuery", required = false) String searchQuery,
                                       @RequestParam(value = "type", required = false) String type,
                                       @RequestParam(value = "status", required = false) String status,
                                       @RequestParam(value = "sortField", required = false) String sortField,
                                       @RequestParam(value = "sortOrder", required = false) String sortOrder,
                                       @RequestParam(value = "clearSearch", required = false) String clearSearch,
                                       Model model) {
        int currentUserId = 1;  // I set this to 1 for now until we sort out the session stuff.
        List<userInventoryItem> items;
        List<userTransaction> checkedOutItems = Collections.emptyList();

        try {
            checkedOutItems = inventoryService.findCheckedOutItemsByUser(currentUserId);
        } catch (SQLException e) {
            model.addAttribute("errorMessage", "Error fetching checked-out items: " + e.getMessage());
        }

        if ("true".equals(clearSearch)) {
            searchQuery = null;
            type = null;
            status = null;
            sortField = null;
            sortOrder = null;
            items = inventoryService.findAllInventoryItems();
        } else {
            items = inventoryService.searchInventoryItems(searchQuery, type, status, sortField, sortOrder);

            if (items.isEmpty()) {
                items = inventoryService.findAllInventoryItems();
            }
        }

        for (userInventoryItem item : items) {
            item.setCheckedOutByUser(false);
            for (userTransaction transaction : checkedOutItems) {
                if (transaction.getItemId() == item.getItemId() && transaction.getCheckinDate() == null) {
                    item.setCheckedOutByUser(true);
                    item.setTransactionId(transaction.getTransactionId());
                    break;
                }
            }
        }


        model.addAttribute("items", items);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("type", type);
        model.addAttribute("status", status);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);

        return "userInventory";
    }


    @PostMapping("/checkout")
    public String checkoutItem(@RequestParam("itemId") int itemId, @RequestParam("userId") int userId, Model model) {
        try {
            inventoryService.checkoutItem(itemId, userId);
            model.addAttribute("successMessage", "Item checked out successfully.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/userInventory";
    }

    @PostMapping("/checkin")
    public String checkinItem(@RequestParam("transactionId") int transactionId, Model model) {
        try {
            inventoryService.checkinItem(transactionId);
            model.addAttribute("successMessage", "Item checked in successfully.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/userInventory";
    }

    @GetMapping("/dashboard")
    public String getUserDashboard(Model model) {
        List<userTransaction> transactions = Collections.emptyList();

        try {
            transactions = inventoryService.findAllTransactions();
        } catch (SQLException e) {
            model.addAttribute("errorMessage", "Error fetching transactions: " + e.getMessage());
        }

        model.addAttribute("transactions", transactions);
        return "Dashboard";
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public String handleRuntimeException(RuntimeException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("items", Collections.emptyList());
        return "userInventory";
    }
}