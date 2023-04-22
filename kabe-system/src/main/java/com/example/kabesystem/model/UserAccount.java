package com.example.kabesystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class UserAccount {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private Boolean isUploader;
    private Boolean isAdmin;
    private String avatar;
    private String nickname;

    public UserAccount() {
    }

    public UserAccount(Long id, String username, String password, String email, Boolean isUploader, Boolean isAdmin, String avatar, String nickname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isUploader = isUploader;
        this.isAdmin = isAdmin;
        this.avatar = avatar;
        this.nickname = nickname;
    }
}
