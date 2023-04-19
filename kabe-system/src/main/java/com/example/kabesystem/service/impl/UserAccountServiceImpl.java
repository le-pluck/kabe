package com.example.kabesystem.service.impl;

import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kabesystem.mapper.UserAccountMapper;
import com.example.kabesystem.model.UserAccount;
import com.example.kabesystem.service.UserAccountService;
import com.example.kabesystem.util.JWTUtil;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

    @Override
    public UserAccount selectOneByUsername(String username) {
        QueryWrapper<UserAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Map<String, Object> verifyPassword(String username, String password) {
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        UserAccount userAccount = selectOneByUsername(username);
        Map<String, Object> result = new HashMap<>();
        if (userAccount == null) {
            result.put("code", 501);
            result.put("msg", "未注册的用户名。");
        } else if (!Objects.equals(userAccount.getPassword(), md5)) {
            result.put("code", 502);
            result.put("msg", "错误的用户名或密码。");
        } else {
            result.put("code", 200);
            result.put("userAccount", userAccount);
        }
        return result;
    }

    @Override
    public String issueToken(UserAccount userAccount) {
        return JWTUtil.issueToken(userAccount);
    }

    @Override
    public Map<String, Claim> verifyToken(String token) {
        return JWTUtil.verifyToken(token);
    }

    @Override
    public UserAccount getInfo(Long id) {
        UserAccount userAccount = baseMapper.selectById(id);
        userAccount.setPassword(null);
        return userAccount;
    }

    @Override
    public String getAvatar(Long id) {
        QueryWrapper<UserAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("avatar");
        queryWrapper.eq("id", id);
        return baseMapper.selectOne(queryWrapper).getAvatar();
    }
}
