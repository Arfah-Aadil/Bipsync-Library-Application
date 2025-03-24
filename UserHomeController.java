package library.libraryapp.controller;

import library.libraryapp.service.UserHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserHomeController {

    private final UserHomeService userHomeService;

    @Autowired
    public UserHomeController(UserHomeService userHomeService) {
        this.userHomeService = userHomeService;
    }

    @GetMapping("/userDashboard")
    public String getUserDashboard(Model model) {
        int userId = 1;

        String userName = userHomeService.getUserNameById(userId);

        String welcomeMessage = (userName != null) ? "Welcome, " + userName + "!" : "Welcome, User!";
        model.addAttribute("welcomeMessage", welcomeMessage);

        List<String> recentActivity = userHomeService.getRecentActivity(userId);
        if (recentActivity.isEmpty()) {
            model.addAttribute("recentActivityMessage", "You have made no recent transactions.");
        } else {
            model.addAttribute("recentActivity", recentActivity);
        }


        List<String> upcomingCheckins = userHomeService.getUpcomingCheckins(userId);
        if (upcomingCheckins.isEmpty()) {
            model.addAttribute("upcomingCheckinsMessage", "No upcoming check-ins.");
        } else {
            model.addAttribute("upcomingCheckins", upcomingCheckins);
        }

        return "userDashboard";
    }


}