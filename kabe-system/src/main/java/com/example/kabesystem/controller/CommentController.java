package com.example.kabesystem.controller;

import com.example.kabesystem.model.Comment;
import com.example.kabesystem.service.CommentService;
import com.example.kabesystem.util.Result;
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
    return Result.success(commentService.createComment(comment, userId));
  }

  @GetMapping("/{postId}")
  public Result<?> getPostCommentsPaged(
      @PathVariable(value = "postId") Long postId,
      @RequestParam Integer pageIndex,
      @RequestParam Integer pageSize,
      @RequestParam String sortingCriteria,
      @RequestAttribute Long userId) {
    return Result.success(
        commentService.getPostCommentsSortedPaged(
            postId, pageIndex, pageSize, sortingCriteria, userId));
  }

  @DeleteMapping("/{commentId}")
  public Result<?> removeComment(
      @PathVariable(value = "commentId") Long commentId,
      @RequestAttribute Long userId,
      @RequestAttribute Boolean isAdmin) {
    if (isAdmin) {
      return Result.success(commentService.removeCommentAdmin(commentId));
    } else {
      return Result.success(commentService.removeComment(commentId, userId));
    }
  }
}
