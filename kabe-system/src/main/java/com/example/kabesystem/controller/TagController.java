package com.example.kabesystem.controller;

import com.example.kabesystem.model.Tag;
import com.example.kabesystem.service.TagService;
import com.example.kabesystem.util.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/{postId}")
    public Result<?> postPostTags(@RequestBody List<Tag> tags, @PathVariable(value = "postId") Long postId) {
        return Result.success(tagService.postPostTags(tags, postId));
    }

    @GetMapping("")
    public Result<?> getTags() {
        return Result.success(tagService.getTags());
    }

    @GetMapping("/post/ids/{name}")
    public Result<?> getPostIdsByTag(@PathVariable(value = "name") String name) {
        return Result.success(tagService.getPostIdsByTagName(name));
    }
}
