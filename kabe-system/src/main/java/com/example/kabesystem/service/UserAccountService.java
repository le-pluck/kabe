package com.example.kabesystem.service;

import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kabesystem.model.UserAccount;

import java.util.Map;

public interface UserAccountService extends IService<UserAccount> {

    UserAccount selectOneByUsername(String username);

    Map<String, Object> verifyPassword(String username, String password);

    String issueToken(UserAccount userAccount);

    Map<String, Claim> verifyToken(String token);
}
