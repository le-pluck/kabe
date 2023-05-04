package com.example.kabesystem.controller;

import com.example.kabesystem.model.UserAccount;
import com.example.kabesystem.service.MailService;
import com.example.kabesystem.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/verification")
    private Result<?> sendVerificationMail(@RequestBody UserAccount userAccount) {
        Map<String, Object> map = mailService.sendVerificationMail(userAccount.getEmail(), userAccount.getUsername());
        return Result.response((int) map.get("code"), (String) map.get("message"), null);
    }
}
