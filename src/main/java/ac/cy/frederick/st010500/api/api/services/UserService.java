package ac.cy.frederick.st010500.api.api.services;

import ac.cy.frederick.st010500.api.api.models.User;
import ac.cy.frederick.st010500.api.api.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service("UserService")
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}