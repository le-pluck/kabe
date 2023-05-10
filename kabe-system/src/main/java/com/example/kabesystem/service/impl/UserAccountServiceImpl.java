package com.example.kabesystem.service.impl;

import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kabesystem.mapper.UserAccountMapper;
import com.example.kabesystem.model.UserAccount;
import com.example.kabesystem.service.UserAccountService;
import com.example.kabesystem.util.JWTUtil;
import com.example.kabesystem.util.RedisUtil;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount>
        implements UserAccountService {

    private final RedisUtil redisUtil;
    private final ResourceLoader resourceLoader;

    public UserAccountServiceImpl(RedisUtil redisUtil, ResourceLoader resourceLoader) {
        this.redisUtil = redisUtil;
        this.resourceLoader = resourceLoader;
    }

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
            result.put("code", 461);
            result.put("message", "未注册的用户名。");
        } else if (!Objects.equals(userAccount.getPassword(), md5)) {
            result.put("code", 462);
            result.put("message", "错误的用户名或密码。");
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

    @Override
    public Map<String, Object> createUserAccount(UserAccount userAccount, String verificationCode) {
        Map<String, Object> map = new HashMap<>();
        String cachedCode = (String) redisUtil.get(userAccount.getEmail());
        if (cachedCode == null) {
            map.put("code", 464);
            map.put("message", "验证码已过期。");
        } else if (!Objects.equals(cachedCode, verificationCode)) {
            map.put("code", 465);
            map.put("message", "不是正确的验证码。");
        } else {
            String md5 =
                    DigestUtils.md5DigestAsHex(
                            userAccount.getPassword().getBytes(StandardCharsets.UTF_8));
            userAccount.setPassword(md5);

            Resource resource = resourceLoader.getResource("classpath:static/defaultAvatar.txt");
            String defaultAvatar = null;
            try {
                InputStream inputStream = resource.getInputStream();
                defaultAvatar = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            userAccount.setAvatar(defaultAvatar);

            userAccount.setIsAdmin(false);
            userAccount.setIsUploader(false);
            userAccount.setNickname("Kabe用户_" + userAccount.getUsername());

            baseMapper.insert(userAccount);
            map.put("code", 201);
            map.put("message", "OK");
        }
        return map;
    }

    @Override
    public String getNickname(Long userId) {
        return baseMapper.selectById(userId).getNickname();
    }

    @Override
    public boolean modifyPassword(Long userId, String oldPassword, String newPassword) {
        String oldPasswordMd5 =
                DigestUtils.md5DigestAsHex(oldPassword.getBytes(StandardCharsets.UTF_8));
        String newPasswordMd5 =
                DigestUtils.md5DigestAsHex(newPassword.getBytes(StandardCharsets.UTF_8));

        LambdaQueryWrapper<UserAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(UserAccount::getId, UserAccount::getPassword).eq(UserAccount::getId, userId);

        UserAccount userAccount = baseMapper.selectOne(wrapper);
        if (!oldPasswordMd5.equals(userAccount.getPassword())) {
            return false;
        }

        userAccount.setPassword(newPasswordMd5);
        return baseMapper.updateById(userAccount) == 1;
    }

    @Override
    public boolean modifyNickname(Long userId, String nickname) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userId);
        userAccount.setNickname(nickname);
        return baseMapper.updateById(userAccount) == 1;
    }

    @Override
    public boolean modifyAvatar(Long userId, UserAccount userAccount) {
        userAccount.setId(userId);
        return baseMapper.updateById(userAccount) == 1;
    }
}
