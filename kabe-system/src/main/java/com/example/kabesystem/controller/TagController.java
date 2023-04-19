package com.example.kabesystem.controller;

import com.example.kabesystem.service.TagService;
import com.example.kabesystem.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{postId}")
    public Result<?> getTagsByPostId(@PathVariable(value = "postId") Long postId) {
        return Result.success(tagService.getTagsByPostId(postId));
    }

    @GetMapping("")
    public Result<?> getTags() {
        return Result.success(tagService.getTags());
    }

    @GetMapping("/post/ids/{tag}")
    public Result<?> getPostIdsByTag(@PathVariable(value = "tag") String tag) {
        return Result.success(tagService.getPostIdsByTag(tag));
    }
}
