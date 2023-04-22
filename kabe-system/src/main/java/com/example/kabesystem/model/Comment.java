package com.example.kabesystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String parentType;
    private Long parentId;
    private String content;
    private Timestamp createTime;
    private Long userId;

    public Comment() {
    }

    public Comment(Long id, String parentType, Long parentId, String content, Timestamp createTime, Long userId) {
        this.id = id;
        this.parentType = parentType;
        this.parentId = parentId;
        this.content = content;
        this.createTime = createTime;
        this.userId = userId;
    }
}
