package library.libraryapp.controller;

import library.libraryapp.model.AdminInventoryItem;
import library.libraryapp.service.AdminInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminInventoryController {

    private final AdminInventoryService service;

    @Autowired
    public AdminInventoryController(AdminInventoryService service) {
        this.service = service;
    }

    @GetMapping("/adminInventory")
    public String getAllInventoryItems(@RequestParam(value = "searchQuery", required = false) String searchQuery,
                                       @RequestParam(value = "type", required = false) String type,
                                       @RequestParam(value = "status", required = false) String status,
                                       @RequestParam(value = "sortField", required = false) String sortField,
                                       @RequestParam(value = "sortOrder", required = false) String sortOrder,
                                       @RequestParam(value = "clearSearch", required = false) String clearSearch,
                                       Model model) {
        List<AdminInventoryItem> items;

        if ("true".equals(clearSearch)) {
            searchQuery = null;
            type = null;
            status = null;
            sortField = null;
            sortOrder = null;
            items = service.findAllInventoryItems();
        } else {
            items = service.searchInventoryItems(searchQuery, type, status, sortField, sortOrder);

            if (items.isEmpty()) {
                items = service.findAllInventoryItems();
            }
        }

        Map<Integer, Boolean> pendingTransactionsMap = new HashMap<>();
        for (AdminInventoryItem item : items) {
            boolean hasPending = service.hasPendingTransactions(item.getItemId());
            pendingTransactionsMap.put(item.getItemId(), hasPending);
        }

        model.addAttribute("items", items);
        model.addAttribute("pendingTransactionsMap", pendingTransactionsMap);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("type", type);
        model.addAttribute("status", status);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);

        return "adminInventory";
    }

    @PostMapping("/adminInventory/approve")
    public ResponseEntity<String> approveTransaction(@RequestParam("itemId") int itemId) {
        System.out.println("Received a POST request for approving item with ID: " + itemId);
        try {
            service.approveItem(itemId);
            return ResponseEntity.ok("Approve successful");
        } catch (Exception e) {
            String errorMessage = "Approve failed: " + e.getMessage();
            System.err.println("Error approving item: " + errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }
    @PostMapping("/adminInventory/delete")
    public ResponseEntity<String> deleteInventoryItem(@RequestParam("itemId") int itemId) {
        try {
            service.deleteItem(itemId);
            return ResponseEntity.ok("Delete successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete failed: " + e.getMessage());
        }
    }

    @PostMapping("/adminInventory/edit")
    public ResponseEntity<String> editInventoryItem(@RequestParam("itemId") int itemId,
                                                    @RequestParam("stock") int stock,
                                                    @RequestParam("location") String location,
                                                    @RequestParam("status") String status) {
        try {
            service.updateItem(itemId, stock, location, status);
            return ResponseEntity.ok("Edit successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Edit failed: " + e.getMessage());
        }
    }

    @PostMapping("/adminInventory/add")
    public ResponseEntity<String> addInventoryItem(
            @RequestParam("type") String type,
            @RequestParam("model") String model,
            @RequestParam("status") String status,
            @RequestParam("stock") int stock,
            @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam("company") String company,
            @RequestParam("acquisitionDate") String acquisitionDate) {
        try {
            service.addItem(type, model, status, stock, description, location, company, acquisitionDate);
            return ResponseEntity.ok("Item added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Add item failed: " + e.getMessage());
        }
    }

}