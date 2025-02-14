package se.ifmo.lab4backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import se.ifmo.lab4backend.components.CustomAuthentication;
import se.ifmo.lab4backend.models.User;
import se.ifmo.lab4backend.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {
    private UserService userService;

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
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        try {
            Optional<String> jwt = userService.login(user);
            if(jwt.isEmpty()) {
                throw new BadCredentialsException("Invalid username or password");
            }
            setAuthContext(user);
            return ResponseEntity.ok(jwt.get());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    private void setAuthContext(User user) {
        CustomAuthentication auth = new CustomAuthentication(
                user.getUsername(),
                user.getId(),
                user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
