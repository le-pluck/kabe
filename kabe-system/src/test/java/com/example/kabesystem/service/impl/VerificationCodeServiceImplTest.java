package com.example.kabesystem.service.impl;

import com.example.kabesystem.service.VerificationCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VerificationCodeServiceImplTest {
    @Autowired
    private VerificationCodeService verificationCodeService;

    @Test
    void generateVerificationCode() {
        String code = verificationCodeService.generateVerificationCode();
        System.out.println(code);
    }
}