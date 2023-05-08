package com.example.kabesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kabesystem.model.CommentFavor;
import java.util.List;
import java.util.Map;

public interface CommentFavorService extends IService<CommentFavor> {
  Map<Long, Void> checkUserFavorOnComments(Long userId, List<Long> commentIds);

  void addFavor(Long userId, Long commentId, Integer favor);

  void cancelFavor(Long userId, Long commentId, Integer favor);

  void removeComment(Long commentId);
}
