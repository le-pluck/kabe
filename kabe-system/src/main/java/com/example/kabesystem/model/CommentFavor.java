package com.example.kabesystem.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentFavor implements Serializable {

  private Long userId;

  private Long commentId;
}
