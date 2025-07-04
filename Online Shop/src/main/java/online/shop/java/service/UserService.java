package online.shop.java.service;

import online.shop.java.model.User;
import online.shop.java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.map(user -> user.getPassword().equals(password)).orElse(false);
    }

    public User register(User user) {
        return userRepository.save(user);
    }
}
