package com.example.kabesystem.service;

public interface MailService {
    public boolean sendVerificationMail(String addressee, String username);
}
