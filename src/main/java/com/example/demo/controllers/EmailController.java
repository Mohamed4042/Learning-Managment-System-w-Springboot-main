package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.EmailDetails;
import com.example.demo.services.EmailService;

@RestController
public class EmailController {
    @Autowired private EmailService emailService;

    // Sending a simple Email
    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        String status
            = emailService.sendSimpleMail(details);

        return status;
    }

    // Sending email with attachment
    @PreAuthorize("hasRole('ADMIN') || hasRole('INSTRUCTOR')")
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
        @RequestBody EmailDetails details)
    {
        String status
            = emailService.sendMailWithAttachment(details);

        return status;
    }
}
