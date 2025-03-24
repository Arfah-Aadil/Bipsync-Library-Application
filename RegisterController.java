package library.libraryapp.Login;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {
    @Autowired
    private LoginUserRepository userRepository;
    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }
    @PostMapping("/register")
    public String newUser(@ModelAttribute registerDTO registrationDTO) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setPassword("{noop}" + registrationDTO.getPassword());
        user.setRole(registrationDTO.getRole());
        userRepository.save(user);
        return "redirect:/login";
    }


}
