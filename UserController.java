package library.libraryapp.controller;

import library.libraryapp.model.Transaction;
import library.libraryapp.model.User;
import library.libraryapp.service.ReminderService;
import library.libraryapp.service.TransactionService;
import library.libraryapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/userManage")  // Updated to /userManage
public class UserController {

    private final UserService userService;
    private final TransactionService transactionService;
    private final ReminderService reminderService;

    @Autowired
    public UserController(UserService userService, TransactionService transactionService, ReminderService reminderService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.reminderService = reminderService;
    }

    // User Management

    @GetMapping("/view")
    public String viewUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user"; // Corresponds to viewUsers.html
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "create"; // Corresponds to createUser.html
    }

    @PostMapping("/save")
    public String saveUser(User user) {
        userService.saveUser(user);
        return "redirect:/userManage/view"; // Redirect to view users page, updated /user to /userManage
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userId") int userId) {
        userService.deleteUser(userId);
        return "redirect:/userManage/view"; // Redirect to view users page, updated /user to /userManage
    }

    // Transaction Management (History and Renewal)

    @GetMapping("/history")
    public String viewTransactionHistory(Model model) {
        List<Transaction> transactions = transactionService.getAllTransactions();
        model.addAttribute("transactions", transactions);
        return "history"; // Corresponds to history.html
    }

//    @GetMapping("/renewal")
//    public String viewUpcomingRenewals(Model model) {
//        List<Transaction> transactions = transactionService.getUpcomingDueDates();
//        model.addAttribute("transactions", transactions);
//        return "renewal"; // Corresponds to renewal.html
//    }
    @GetMapping("/renewal")
    public String viewUpcomingRenewals(Model model) {
        List<Transaction> transactions = transactionService.getUpcomingDueDates();
        for (Transaction transaction : transactions) {
            User user = userService.getUserById(transaction.getUserId());
            if (user != null) {
                transaction.setEmail(user.getEmail());
            }
        }

        model.addAttribute("transactions", transactions);
        return "renewal";
    }


    @PostMapping("/sendReminders")
    public String sendReminders(@RequestParam(name = "transactionId", required = false) Long transactionId) {
        if (transactionId != null) {
            Transaction transaction = transactionService.getTransactionById(transactionId);
            User user = userService.getUserById(transaction.getUserId());

            if (transaction != null && user != null) {
                String email = user.getEmail();
                String subject = "Reminder: Upcoming Due Date";
                String text = "Dear " + user.getUsername() + ",\n\nThis is a reminder that your borrowed item \""
                        + transaction.getModel() + "\" is due in 3 days (Due Date: " + transaction.getDueDate()
                        + "). Please return it on time to avoid any penalties.\n\nThank you,\nLibrary Team";

                // Email sending logic (omitted for brevity)
            } else {
                System.out.println("Transaction or user not found");
            }
        } else {
            System.out.println("Transaction ID is null");
        }
        return "redirect:/userManage/renewal"; // Updated /user to /userManage
    }
}
