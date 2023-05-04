package com.example.kabesystem.controller;

import com.example.kabesystem.model.Comment;
import com.example.kabesystem.service.CommentService;
import com.example.kabesystem.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("")
    public Result<?> postComment(@RequestBody Comment comment, @RequestAttribute Long userId) {
        commentService.createComment(comment, userId);
        return Result.postSuccess(null);
    }

    @GetMapping("/{postId}")
    public Result<?> getPostCommentsPaged(
            @PathVariable(value = "postId") Long postId,
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize) {
        return Result.success(commentService.getPostCommentsPaged(postId, pageIndex, pageSize));
    }
}
