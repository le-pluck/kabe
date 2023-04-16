package com.example.kabesystem.service.impl;

import com.example.kabesystem.service.MailService;
import com.example.kabesystem.service.VerificationCodeService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailServiceImpl implements MailService {
    private final VerificationCodeService verificationCodeService;
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailServiceImpl(VerificationCodeService verificationCodeService, TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.verificationCodeService = verificationCodeService;
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    @Value("${mail.from-address}")
    private String fromAddress;
    @Value("${mail.subject.verification}")
    private String subject;

    @Override
    public boolean sendVerificationMail(String addressee, String username) {
        String verificationCode = verificationCodeService.generateVerificationCode();
        Context context = new Context();
        context.setVariable("verificationCode", verificationCode);
        context.setVariable("username", username);
        String emailContent = templateEngine.process("EmailVerification", context);
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromAddress);
            helper.setTo(addressee);
            helper.setSubject(subject);
            helper.setText(emailContent, true);
            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
