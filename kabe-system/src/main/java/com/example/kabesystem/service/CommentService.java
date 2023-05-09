package com.example.kabesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kabesystem.model.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService extends IService<Comment> {
    boolean createComment(Comment comment, Long userId);

    Map<String, Object> getPostCommentsSortedPaged(
            Long postId, Integer pageIndex, Integer pageSize, String sortingCriteria, Long userId);

    void addFavor(Long userId, Long commentId, Integer favor);

    void cancelFavor(Long userId, Long commentId, Integer favor);

    boolean removeComment(Long commentId, Long userId);

    boolean removeCommentAdmin(Long commentId);

    List<Long> getAllCommentIdsByPostId(Long postId);

    boolean removeAllCommentByCommentIds(List<Long> commentIds);
}
