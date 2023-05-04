package com.example.kabesystem.dto.comment;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentResponseDTO {
    private Long id;
    private String parentType;
    private Long storageParentId;
    private Long logicalParentId;
    private String content;
    private Timestamp createTime;
    private Long userId;
    private String nickname;
    private String avatar;
    private String parentNickname;

    public CommentResponseDTO() {
    }

    public CommentResponseDTO(Long id, String parentType, Long storageParentId, Long logicalParentId, String content, Timestamp createTime, Long userId, String nickname, String avatar, String parentNickname) {
        this.id = id;
        this.parentType = parentType;
        this.storageParentId = storageParentId;
        this.logicalParentId = logicalParentId;
        this.content = content;
        this.createTime = createTime;
        this.userId = userId;
        this.nickname = nickname;
        this.avatar = avatar;
        this.parentNickname = parentNickname;
    }
}