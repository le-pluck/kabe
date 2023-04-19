package com.example.kabesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kabesystem.mapper.PostMapper;
import com.example.kabesystem.model.Post;
import com.example.kabesystem.service.PostService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Override
    public Post selectById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public int postPost(Post post) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        post.setCreateTime(timestamp);
        post.setUpdateTime(timestamp);
        return baseMapper.insert(post);
    }

    @Override
    public Post getPostPreviewById(Long id) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("id, poster_id, title, subtitle, reaction, star, create_time, update_time")
                .eq("id", id);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<Post> getPostPreviewsByIds(List<Long> ids) {
        return null;
    }

    @Override
    public List<Post> getPostPreviewsLatestPaged(Integer pageIndex, Integer pageSize) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");

        Page<Post> page = new Page<>(pageIndex, pageSize);

        page = baseMapper.selectPage(page, queryWrapper);

        return page.getRecords();
    }

    @Override
    public List<Post> getPostPreviewsByPosterId(Long posterId) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("id, poster_id, title, subtitle, reaction, star, create_time, update_time")
                .eq("poster_id", posterId);
        return baseMapper.selectList(queryWrapper);
    }
}
