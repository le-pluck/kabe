package com.example.kabesystem.dto.comment;

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
    private Integer favor;
    private String nickname;
    private String avatar;
    private String parentNickname;
    private Boolean favored;

    public CommentResponseDTO() {
    }

    public CommentResponseDTO(Long id, String parentType, Long storageParentId, Long logicalParentId, String content, Timestamp createTime, Long userId, Integer favor, String nickname, String avatar, String parentNickname, Boolean favored) {
        this.id = id;
        this.parentType = parentType;
        this.storageParentId = storageParentId;
        this.logicalParentId = logicalParentId;
        this.content = content;
        this.createTime = createTime;
        this.userId = userId;
        this.favor = favor;
        this.nickname = nickname;
        this.avatar = avatar;
        this.parentNickname = parentNickname;
        this.favored = favored;
    }
}