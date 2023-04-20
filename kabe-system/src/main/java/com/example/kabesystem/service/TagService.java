package com.example.kabesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kabesystem.model.Tag;

import java.util.List;

public interface TagService extends IService<Tag> {
    List<Tag> getTagsByPostId(Long postId);
    List<Tag> getTags();
    List<Long> getPostIdsByTagName(String name);
    boolean postPostTags(List<Tag> tags, Long postId);
}
