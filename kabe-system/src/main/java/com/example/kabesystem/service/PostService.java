package com.example.kabesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kabesystem.model.Post;

import java.util.List;
import java.util.Map;

public interface PostService extends IService<Post> {
    Post selectById(Long id);
    Long postPost(Post post, Long posterId);
    Post getPostPreviewById(Long id);
    List<Post> getPostPreviewsByIds(List<Long> ids);
    Map<String, Object> getPostPreviewsLatestPaged(Integer pageIndex, Integer pageSize);
    List<Post> getPostPreviewsByPosterId(Long posterId);
}
