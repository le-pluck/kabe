package com.example.kabesystem.service.mail;

import org.springframework.stereotype.Service;

public interface VerificationCodeService {
    String generateVerificationCode();
}
