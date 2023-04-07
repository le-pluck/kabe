package com.example.kabesystem.service.impl;

import com.example.kabesystem.service.MailService;
import com.example.kabesystem.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Override
    public void sendMail(String to, String subject, String verifyCode) {

    }
}
