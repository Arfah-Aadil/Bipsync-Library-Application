//package library.libraryapp.service;
//
//import library.libraryapp.model.User;
//import library.libraryapp.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    public void deleteUser(int userId) {
//        userRepository.deleteById(userId);
//    }
//
//    public void saveUser(User user) {
//        userRepository.saveUser(user);
//    }
//}
package library.libraryapp.service;

import library.libraryapp.model.User;
import library.libraryapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }
    public User getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public void saveUser(User user) {
        userRepository.saveUser(user);
    }
}
