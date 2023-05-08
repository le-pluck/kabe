package com.example.kabesystem.controller;

import com.example.kabesystem.service.CommentService;
import com.example.kabesystem.util.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment/favor")
public class CommentFavorController {

  private final CommentService commentService;

  public CommentFavorController(CommentService commentService) {
    this.commentService = commentService;
  }

  @PostMapping("/{commentId}")
  public Result<?> addFavor(
      @RequestAttribute Long userId,
      @PathVariable(value = "commentId") Long commentId,
      @RequestParam Integer favor) {
    commentService.addFavor(userId, commentId, favor);
    return Result.postSuccess(null);
  }

  @DeleteMapping("/{commentId}")
  public Result<?> cancelFavor(
      @RequestAttribute Long userId,
      @PathVariable(value = "commentId") Long commentId,
      @RequestParam Integer favor) {
    commentService.cancelFavor(userId, commentId, favor);
    return Result.postSuccess(null);
  }
}
