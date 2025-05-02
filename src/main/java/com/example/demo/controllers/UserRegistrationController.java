package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.demo.models.User;
import com.example.demo.models.AuthRequest;
import com.example.demo.services.JwtService;
import com.example.demo.services.UserRegistrationService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
public class UserRegistrationController {
    @Autowired
    private UserRegistrationService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            //for user role validation
            if (!List.of("ADMIN", "INSTRUCTOR", "STUDENT").contains(user.getRole().toUpperCase())) {
                return ResponseEntity.status(400).body("incorrect role");
            }
            //for saving the user and econde his password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.addPerson(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        try {
            User user = userService.getUser(authRequest.getUsername());
            if (user != null && passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                String token = jwtService.generateToken(user.getUsername());
                return ResponseEntity.ok(token);
            }
            return ResponseEntity.status(401).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/viewall")
    public ResponseEntity<List<User>> getAll() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @PutMapping("/updateProfile/{id}")
    public ResponseEntity<String> updateProfile(@PathVariable int id, @RequestBody User updatedUser) {
        try {
            User existingUser = userService.getUserById(id);
            if (existingUser != null) {
                existingUser.setUsername(updatedUser.getUsername());
                existingUser.setEmail(updatedUser.getEmail());
                userService.addPerson(existingUser);
                return ResponseEntity.ok("Profile updated successfully");
            } else {
                return ResponseEntity.status(404).body("user not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("error updating profile");
        }
    }


    @GetMapping("/viewProfile/{id}")
    public ResponseEntity<User> viewProfile(@PathVariable int id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.status(404).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}