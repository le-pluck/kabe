package com.example.kabesystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
