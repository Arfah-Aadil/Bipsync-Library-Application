package library.libraryapp.Login;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private LoginUserRepository userRepository;
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(g -> g.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/transactions";
        } else {
            return "redirect:/userDashboard";
        }
    }
    @GetMapping("/forgot-password")
    public String forgotPassword(){
        return "forgot-password";
    }
    @PostMapping("/forgot-password")
    public String forgotPasswordService(@RequestParam String username, Model model){
        User user = userRepository.findByUsername(username);
        if (user == null){
            model.addAttribute("Error 404", "This user does nt exist");
            return "forgot-password";
        }
        model.addAttribute("username", username);
        return "redirect:/reset-password?username=" + username;
    }
    @GetMapping("/reset-password")
    public String changePassword(@RequestParam String username, Model model){
        model.addAttribute("username", username);
        return "reset-password";
    }
    @PostMapping("/reset-password")
    public String changePasswordService(@RequestParam String username, @RequestParam String newPassword, Model model){
        User user = userRepository.findByUsername(username);
        if (user == null){
            model.addAttribute("error", "This user doesn't exist");
            return "reset-password";
        }
        System.out.println("Old Password: " + user.getPassword());
        user.setPassword("{noop}" + newPassword);
        userRepository.save(user);
        System.out.println("New Password: " + user.getPassword());
        model.addAttribute("message", "Password changed!");
        return "redirect:/login";
    }
}
