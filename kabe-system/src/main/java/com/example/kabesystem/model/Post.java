package com.example.kabesystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.example.kabesystem.util.LongTextTypeHandler;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
