package se.ifmo.lab4backend.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import se.ifmo.lab4backend.components.CustomAuthentication;
import se.ifmo.lab4backend.models.User;
import se.ifmo.lab4backend.services.UserService;

import java.util.Map;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        try {
            userService.register(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        try {
            Optional<String> jwtOptional = userService.login(user);
            if (jwtOptional.isEmpty()) {
                throw new BadCredentialsException("Invalid username or password");
            }
            Optional<User> registeredUser = userService.getUserByUsername(username);
            registeredUser.ifPresent(this::setAuthContext);
            return ResponseEntity.ok(Map.of("token", jwtOptional.get()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
    }

    private void setAuthContext(User user) {
        log.info("AuthContext for user: " + user.getUsername() + ", " + user.getId());
        if (user.getId() == null) {
            return;
        }
        CustomAuthentication authentication = new CustomAuthentication(
                user.getUsername(),
                user.getId(),
                user.getAuthorities()
        );
        log.info("Current authentication: {}, {}", authentication, authentication.getUserId());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
