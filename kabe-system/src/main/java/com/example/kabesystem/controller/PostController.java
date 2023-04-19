package com.example.kabesystem.controller;

import com.example.kabesystem.model.Post;
import com.example.kabesystem.service.PostService;
import com.example.kabesystem.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public Result<?> getPostById(@PathVariable(value = "id") Long id) {
        return Result.success(postService.selectById(id));
    }

    @PostMapping("")
    public Result<?> postPost(@RequestBody Post post) {
        return Result.postSuccess(postService.postPost(post));
    }

    @GetMapping("/preview/{id}")
    public Result<?> getPostPreviewById(@PathVariable(value = "id") Long id) {
        return Result.success(postService.selectById(id));
    }

    @GetMapping("/preview/latest")
    public Result<?> getPostPreviewsLatestPaged(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        return Result.success(postService.getPostPreviewsLatestPaged(pageIndex, pageSize));
    }

    @GetMapping("/preview/poster/{posterId}")
    public Result<?> getPostPreviewsByPosterId(@PathVariable(value = "posterId") Long posterId) {
        return Result.success(postService.getPostPreviewsByPosterId(posterId));
    }
}
