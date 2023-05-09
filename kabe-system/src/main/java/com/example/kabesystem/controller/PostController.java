package com.example.kabesystem.controller;

import com.example.kabesystem.model.Post;
import com.example.kabesystem.service.PostService;
import com.example.kabesystem.util.Result;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    public Result<?> getPostById(@PathVariable(value = "postId") Long postId) {
        return Result.success(postService.selectById(postId));
    }

    @PostMapping("")
    public Result<?> postPost(@RequestBody Post post, @RequestAttribute Long userId) {
        return Result.postSuccess(postService.createPost(post, userId));
    }

    @GetMapping("/preview/{postId}")
    public Result<?> getPostPreviewById(@PathVariable(value = "postId") Long postId) {
        return Result.success(postService.selectById(postId));
    }

    @GetMapping("/preview/latest")
    public Result<?> getPostPreviewsLatestPaged(
            @RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return Result.success(postService.getPostPreviewsLatestPaged(pageIndex, pageSize));
    }

    @GetMapping("/preview/poster/{posterId}")
    public Result<?> getPostPreviewsByPosterId(@PathVariable(value = "posterId") Long posterId) {
        return Result.success(postService.getPostPreviewsByPosterId(posterId));
    }

    @DeleteMapping("/{postId}")
    public Result<?> deletePostById(
            @PathVariable(value = "postId") Long postId,
            @RequestAttribute Long userId,
            @RequestAttribute Boolean isAdmin) {
        System.out.println("userId " + userId + ", isAdmin " + isAdmin);
        boolean success;
        if (isAdmin) {
            success = postService.deletePostByIdAdmin(postId);
        } else {
            success = postService.deletePostById(postId, userId);
        }
        return Result.success(success);
    }
}
