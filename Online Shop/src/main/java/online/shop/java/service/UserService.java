package online.shop.java.service;

import com.retail.supershop.app.entity.User;
import com.retail.supershop.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
    }

    public User authenticate(String username, String rawPassword) {
        User user = repo.findByUsername(username);
        if (user != null && encoder.matches(rawPassword, user.getPassword())) {
            return user;
        }
        return null;
    }
}
