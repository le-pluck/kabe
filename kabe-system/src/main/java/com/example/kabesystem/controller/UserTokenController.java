package com.example.kabesystem.controller;

import com.example.kabesystem.model.user.UserAccount;
import com.example.kabesystem.service.user.UserTokenService;
import com.example.kabesystem.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("user-token")
public class UserTokenController {
    private final UserTokenService userTokenService;

    public UserTokenController(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

    @PostMapping("token")
    public Result<?> postToken(@RequestBody UserAccount userAccount) {
        Map<String, Object> map = userTokenService.verifyPassword(userAccount.getUsername(), userAccount.getPassword());
        if ((map.get("code")).equals(200) ) {
            return Result.success(userTokenService.setLogin((UserAccount)map.get("userAccount")));
        } else {
            return Result.failure((int)map.get("code"), (String)map.get("msg"), null);
        }
    }
}
