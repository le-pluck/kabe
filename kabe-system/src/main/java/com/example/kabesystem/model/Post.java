package com.example.kabesystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.example.kabesystem.util.LongTextTypeHandler;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@TableName(autoResultMap = true)
public class Post {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long posterId;
    private String title;
    private String subtitle;
    private Integer reaction;
    private Integer star;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Object> ops;
    @TableField(typeHandler = LongTextTypeHandler.class)
    private String html;
    private Timestamp createTime;
    private Timestamp updateTime;

    public Post() {
    }

    public Post(Long id, Long posterId, String title, String subtitle, Integer reaction, Integer star, List<Object> ops, String html, Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.posterId = posterId;
        this.title = title;
        this.subtitle = subtitle;
        this.reaction = reaction;
        this.star = star;
        this.ops = ops;
        this.html = html;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
