package se.ifmo.lab4backend.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import se.ifmo.lab4backend.models.User;
import se.ifmo.lab4backend.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Log4j2
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        log.info("Registering user: " + user);
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        log.info(user.toString());
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
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}
