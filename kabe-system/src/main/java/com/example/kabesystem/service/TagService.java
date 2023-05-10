package com.example.kabesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kabesystem.model.Tag;

import java.util.List;

public interface TagService extends IService<Tag> {
    List<Tag> getTagsByPostId(Long postId);

    void removeTagsByPostId(Long postId);

    List<Tag> getTags();

    List<Long> getPostIdsByTagName(String name);

    boolean createPostTags(List<Tag> tags, Long postId);

    String getIconByTagName(String tagName);
}
