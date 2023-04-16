package com.example.kabesystem.controller;

import com.example.kabesystem.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    MailService mailService;

    @PostMapping("/verification")
    private boolean sendVerificationMail(@RequestParam String addressee, @RequestParam String username) {
        return mailService.sendVerificationMail(addressee, username);
    }
}
