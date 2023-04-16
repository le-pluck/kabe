package com.example.kabesystem.controller;

import com.example.kabesystem.model.UserAccount;
import com.example.kabesystem.service.UserAccountService;
import com.example.kabesystem.util.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/account")
public class UserAccountController {
    private final UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/token")
    public Result<?> postToken(@RequestBody UserAccount userAccount) {
        Map<String, Object> map = userAccountService.verifyPassword(userAccount.getUsername(), userAccount.getPassword());
        if ((map.get("code")).equals(200)) {
            return Result.success(
                    userAccountService.issueToken(
                            (UserAccount) map.get("userAccount")
                    )
            );
        } else {
            return Result.failure(
                    (int) map.get("code"),
                    (String) map.get("msg"),
                    null
            );
        }
    }

    @GetMapping("/any")
    public Result<Map<String, Object>> getAny(@RequestAttribute String token, @RequestAttribute Long userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("userId", userId);
        return Result.success(map);
    }
}
