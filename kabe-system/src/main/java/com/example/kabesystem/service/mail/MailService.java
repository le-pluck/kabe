package com.example.kabesystem.service.mail;

public interface MailService {
    public boolean sendVerificationMail(String addressee, String username);
}
