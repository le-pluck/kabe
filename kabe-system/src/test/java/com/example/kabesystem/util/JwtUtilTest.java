package com.example.kabesystem.util;

import com.auth0.jwt.interfaces.Claim;
import com.example.kabesystem.model.UserAccount;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
class JwtUtilTest {

    @Resource
    private JwtUtil jwtUtil;

    @Test
    void verifyToken() {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(114514L);
        userAccount.setUsername("jinx");

        String token = JwtUtil.issueToken(userAccount);

        Map<String, Claim> claims = JwtUtil.verifyToken(token);

        assert claims != null;
        System.out.println(claims.toString());
    }

    @Test
    void pickValue() {
        JwtUtil.pickValue();
    }
}