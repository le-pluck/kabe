package com.example.kabesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.kabesystem.model.Post;

public interface PostService extends IService<Post> {
    Post selectById(Long id);
}
