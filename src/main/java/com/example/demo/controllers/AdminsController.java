package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.demo.services.UserRegistrationService;


@RestController
@RequestMapping("/admin")
public class AdminsController {
    @Autowired
    private UserRegistrationService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-user")
    public ResponseEntity<String> createUser() {
        return ResponseEntity.ok("You are authorized to create a user.");
    }

    //should add manage courses

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") int id) {
        try {
            userService.deleteUser(userService.getUserById(id).getUsername());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}