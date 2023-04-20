package com.example.kabesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kabesystem.mapper.TagMapper;
import com.example.kabesystem.model.Tag;
import com.example.kabesystem.service.TagService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public List<Tag> getTagsByPostId(Long postId) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("name, icon")
                .eq("post_id", postId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Tag> getTags() {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT name, icon");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Long> getPostIdsByTagName(String name) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("post_id")
                .eq("name", name);
        List<Tag> tags = baseMapper.selectList(queryWrapper);

        List<Long> postIds = new ArrayList<>();
        for (Tag eachTag :
                tags) {
            postIds.add(eachTag.getPostId());
        }

        return postIds;
    }

    @Override
    public boolean postPostTags(List<Tag> tags, Long postId) {
        try {
            for (Tag tag :
                    tags) {
                tag.setPostId(postId);
                baseMapper.insert(tag);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
