package com.example.kabesystem.controller;

import com.example.kabesystem.model.UserAccount;
import com.example.kabesystem.service.UserAccountService;
import com.example.kabesystem.util.Result;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/account")
public class UserAccountController {
  private final UserAccountService userAccountService;

  public UserAccountController(UserAccountService userAccountService) {
    this.userAccountService = userAccountService;
  }

  @PostMapping("/token")
  public Result<?> postToken(@RequestBody UserAccount userAccount) {
    Map<String, Object> map =
        userAccountService.verifyPassword(userAccount.getUsername(), userAccount.getPassword());
    if ((map.get("code")).equals(200)) {
      return Result.success(userAccountService.issueToken((UserAccount) map.get("userAccount")));
    } else {
      return Result.failure((int) map.get("code"), (String) map.get("message"), null);
    }
  }

  @GetMapping("/avatar")
  public Result<?> getAvatarCurrent(@RequestAttribute Long userId) {
    Map<String, Object> map = new HashMap<>();
    map.put("avatar", userAccountService.getAvatar(userId));
    return Result.success(map);
  }

  @GetMapping("/avatar/{id}")
  public Result<?> getAvatarById(@PathVariable(value = "id") Long id) {
    Map<String, Object> map = new HashMap<>();
    map.put("avatar", userAccountService.getAvatar(id));
    return Result.success(map);
  }

  @GetMapping("/info")
  public Result<?> getInfoCurrent(@RequestAttribute Long userId) {
    UserAccount userAccount = userAccountService.getInfo(userId);
    System.out.println("======================== userAccount.toString() ========================");
    System.out.println(userAccount.toString());
    return Result.success(userAccount);
  }

  @GetMapping("/info/{id}")
  public Result<?> getInfoById(@PathVariable(value = "id") Long id) {
    return Result.success(userAccountService.getInfo(id));
  }

  @PostMapping("")
  public Result<?> createUserAccount(
      @RequestBody UserAccount userAccount, @RequestParam String code) {
    Map<String, Object> map = userAccountService.createUserAccount(userAccount, code);
    return Result.response((int) map.get("code"), (String) map.get("message"), null);
  }

  @GetMapping("/nickname/{id}")
  public Result<?> getNickname(@PathVariable(value = "id") Long id) {
    return Result.success(userAccountService.getNickname(id));
  }

  @GetMapping("/id")
  public Result<?> authenticateToken(@RequestAttribute Long userId) {
    return Result.success(userId);
  }

  @GetMapping("/permission")
  public Result<?> getPermission(
      @RequestAttribute Long userId,
      @RequestAttribute Boolean isUploader,
      @RequestAttribute Boolean isAdmin) {
    Map<String, Object> map = new HashMap<>(3);
    map.put("userId", userId);
    map.put("isUploader", isUploader);
    map.put("isAdmin", isAdmin);
    return Result.success(map);
  }
}
