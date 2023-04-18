package com.example.kabesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kabesystem.mapper.PostMapper;
import com.example.kabesystem.model.Post;
import com.example.kabesystem.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Override
    public Post selectById(Long id) {
        return baseMapper.selectById(id);
    }
}
