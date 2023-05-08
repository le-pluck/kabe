package com.example.kabesystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
  @TableId(type = IdType.AUTO)
  private Long id;

  private String parentType;
  private Long storageParentId;
  private Long logicalParentId;
  private String content;
  private Timestamp createTime;
  private Long userId;
  private Integer favor;
}
