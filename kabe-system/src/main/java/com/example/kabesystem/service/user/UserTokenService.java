package com.example.kabesystem.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kabesystem.model.user.UserAccount;
import com.example.kabesystem.model.user.UserToken;

import java.util.Map;

public interface UserTokenService extends IService<UserToken> {

    public UserToken verifyToken(String token);

    public Map<String, Object> setLogin(UserAccount userAccount);

    public Map<String, Object> verifyPassword(String name, String password);
}
