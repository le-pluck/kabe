package com.example.kabesystem.service.impl;

import com.example.kabesystem.service.MailService;
import com.example.kabesystem.service.UserAccountService;
import com.example.kabesystem.service.VerificationCodeService;
import com.example.kabesystem.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {
    private final VerificationCodeService verificationCodeService;
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private final UserAccountService userAccountService;

    @Autowired
    public MailServiceImpl(
            VerificationCodeService verificationCodeService,
            TemplateEngine templateEngine,
            JavaMailSender javaMailSender,
            RedisTemplate redisTemplate,
            RedisUtil redisUtil,
            UserAccountService userAccountService) {
        this.verificationCodeService = verificationCodeService;
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.redisUtil = redisUtil;
        this.userAccountService = userAccountService;
    }

    

    private static String FROM_ADDRESS;
    @Value("${mail.from-address}")
    public void setFROM_ADDRESS(String FROM_ADDRESS) {
        MailServiceImpl.FROM_ADDRESS = FROM_ADDRESS;
    }

    private static String SUBJECT;
    @Value("${mail.subject.verification}")
    public void setSUBJECT(String SUBJECT) {
        MailServiceImpl.SUBJECT = SUBJECT;
    }
    
    private static Long EXPIRATION;
    @Value("${mail.verification-code.expiration}")
    public void setEXPIRATION(Long EXPIRATION) {
        MailServiceImpl.EXPIRATION = EXPIRATION;
    }

    @Override
    public Map<String, Object> sendVerificationMail(String addressee, String username) {
        Map<String, Object> map = new HashMap<>();
        if (userAccountService.selectOneByUsername(username) != null) {
            map.put("code", 463);
            map.put("message", "此用户名已被注册。");
            return map;
        }

        String verificationCode = verificationCodeService.generateVerificationCode();
        Context context = new Context();
        context.setVariable("verificationCode", verificationCode);
        context.setVariable("username", username);
        context.setVariable("expiration", EXPIRATION);
        String emailContent = templateEngine.process("EmailVerification", context);
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(FROM_ADDRESS);
            helper.setTo(addressee);
            helper.setSubject(SUBJECT);
            helper.setText(emailContent, true);
            javaMailSender.send(message);

            redisUtil.set(addressee, verificationCode, EXPIRATION);

            map.put("code", 201);
            map.put("message", "OK");
            return map;
        } catch (MessagingException e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("message", "服务器错误，邮件发送过程中遭遇未知失败，请查看服务器日志。");
            return map;
        }
    }
}
