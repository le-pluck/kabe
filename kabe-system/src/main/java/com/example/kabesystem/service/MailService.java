package com.example.kabesystem.service;

import java.util.Map;

public interface MailService {
    public Map<String, Object> sendVerificationMail(String addressee, String username);
}
