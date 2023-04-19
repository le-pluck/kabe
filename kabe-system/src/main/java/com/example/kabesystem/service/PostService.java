package com.example.kabesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kabesystem.model.Post;

import java.util.List;

public interface PostService extends IService<Post> {
    Post selectById(Long id);
    int postPost(Post post);
    Post getPostPreviewById(Long id);
    List<Post> getPostPreviewsByIds(List<Long> ids);
    List<Post> getPostPreviewsLatestPaged(Integer pageIndex, Integer pageSize);
    List<Post> getPostPreviewsByPosterId(Long posterId);
}
