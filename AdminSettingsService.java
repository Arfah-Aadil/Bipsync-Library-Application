package library.libraryapp.AdminSettings;

import library.libraryapp.Login.User;
import library.libraryapp.Login.LoginUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminSettingsService {

    @Autowired
    private LoginUserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword("{noop}" + newPassword);
        userRepository.save(user);
    }

    public void updateNotificationSettings(User user) {
        userRepository.save(user);
    }
}
