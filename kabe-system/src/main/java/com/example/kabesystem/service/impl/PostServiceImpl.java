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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Override
    public Post selectById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Long postPost(Post post, Long posterId) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        post.setPosterId(posterId);
        post.setReaction(0);
        post.setStar(0);
        post.setCreateTime(timestamp);
        post.setUpdateTime(timestamp);
        baseMapper.insert(post);
        return post.getId();
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
    public Map<String, Object> getPostPreviewsLatestPaged(Integer pageIndex, Integer pageSize) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("id, poster_id, title, subtitle, reaction, star, create_time, update_time")
                .orderByDesc("create_time");

        Page<Post> page = new Page<>(pageIndex, pageSize);

        page = baseMapper.selectPage(page, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("pages", page.getPages());
        map.put("total", page.getTotal());
        map.put("postPreviews", page.getRecords());
        return map;
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
