package se.ifmo.lab4backend.services;

import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.ifmo.lab4backend.models.Role;
import se.ifmo.lab4backend.models.User;
import se.ifmo.lab4backend.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private JwtTokenUtil jwtTokenUtil;

    public UserService(UserRepository userRepository, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<String> login(User user) {
        Optional<User> foundUser = getUserByUsername(user.getUsername());
        return foundUser.map(account -> jwtTokenUtil.generateToken(account.getId(), account.getUsername()));
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void register(User user) throws BadCredentialsException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new BadCredentialsException("Такой пользователь уже существует!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
    }
}
