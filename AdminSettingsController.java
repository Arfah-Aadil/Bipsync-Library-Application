package library.libraryapp.AdminSettings;

import library.libraryapp.Login.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/settings")
public class AdminSettingsController {

    @Autowired
    private AdminSettingsService adminSettingsService;

    @GetMapping
    public String showSettings(Model model, Authentication authentication) {
        User user = adminSettingsService.getUserByUsername(authentication.getName());
        model.addAttribute("user", user);
        return "adminSettings";
    }

    @PostMapping("/changePassword")
    public String changePassword(Authentication authentication,
                                 @RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {
        User user = adminSettingsService.getUserByUsername(authentication.getName());

        if (!user.getPassword().equals("{noop}" + currentPassword)) {
            model.addAttribute("error", "Current password is incorrect.");
            return "adminSettings";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New passwords do not match.");
            return "adminSettings";
        }

        adminSettingsService.updatePassword(user, newPassword);
        model.addAttribute("message", "Password changed successfully.");
        return "adminSettings";
    }

    @PostMapping("/updateNotifications")
    public String updateNotifications(@RequestParam(value = "receiveRenewalUpdates", required = false) boolean receiveRenewalUpdates,
                                      @RequestParam(value = "receiveStockUpdates", required = false) boolean receiveStockUpdates,
                                      Model model, Authentication authentication) {
        User user = adminSettingsService.getUserByUsername(authentication.getName());
        adminSettingsService.updateNotificationSettings(user);
        model.addAttribute("notificationMessage", "Notification settings updated successfully.");
        model.addAttribute("user", user);
        return "adminSettings";
    }
}

