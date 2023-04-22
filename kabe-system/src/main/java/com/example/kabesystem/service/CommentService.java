package com.example.kabesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kabesystem.model.Comment;

import java.util.Map;

public interface CommentService extends IService<Comment> {
    void createComment(Comment comment, Long userId);
    Map<String, Object> getPostCommentsPaged(Long postId, Integer pageIndex, Integer pageSize);
}
