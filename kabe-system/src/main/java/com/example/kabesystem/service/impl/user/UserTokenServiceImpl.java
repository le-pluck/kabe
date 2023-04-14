package com.example.kabesystem.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kabesystem.mapper.user.UserTokenMapper;
import com.example.kabesystem.model.user.UserAccount;
import com.example.kabesystem.model.user.UserToken;
import com.example.kabesystem.service.user.UserAccountService;
import com.example.kabesystem.service.user.UserTokenService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, UserToken> implements UserTokenService {
    private final UserAccountService userAccountService;

    public UserTokenServiceImpl(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Override
    public UserToken verifyToken(String token) {
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token", token);
        queryWrapper.eq("removed", false);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Map<String, Object> setLogin(UserAccount userAccount) {
        UserToken userToken = new UserToken(
                null,
                UUID.randomUUID().toString(),
                userAccount.getId(),
                new Timestamp(new Date().getTime() + 86400000),
                false,
                new Timestamp(new Date().getTime())
        );
        baseMapper.insert(userToken);

        // 返回 token
        Map<String, Object> map = new HashMap<>();
        map.put("token", userToken.getToken());
        map.put("userId", userToken.getUserId());
        return map;
    }

    @Override
    public Map<String, Object> verifyPassword(String username, String password) {
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        UserAccount userAccount = userAccountService.selectOneByUsername(username);
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
}
