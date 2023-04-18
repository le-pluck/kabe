package com.example.kabesystem.controller;

import com.example.kabesystem.model.Post;
import com.example.kabesystem.service.PostService;
import com.example.kabesystem.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        return Result.postSuccess(postService.save(post));
    }
}
