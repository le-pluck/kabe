package com.example.kabesystem.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserToken {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String token;
    private Long userId;
    private Timestamp expiredTime;
    private Boolean removed;
    private Timestamp createdTime;

    public UserToken() {
    }

    public UserToken(Long id, String token, Long userId, Timestamp expiredTime, Boolean removed, Timestamp createdTime) {
        this.id = id;
        this.token = token;
        this.userId = userId;
        this.expiredTime = expiredTime;
        this.removed = removed;
        this.createdTime = createdTime;
    }
}
