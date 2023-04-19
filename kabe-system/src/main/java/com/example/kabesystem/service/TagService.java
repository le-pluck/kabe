package com.example.kabesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kabesystem.model.Tag;

import java.util.List;

public interface TagService extends IService<Tag> {
    List<String> getTagsByPostId(Long postId);
    List<String> getTags();
    List<Long> getPostIdsByTag(String tag);
}
