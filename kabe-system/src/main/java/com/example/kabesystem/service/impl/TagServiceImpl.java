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
    public List<String> getTagsByPostId(Long postId) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("post_id", postId)
                .select("tag");
        List<Tag> tags = baseMapper.selectList(queryWrapper);

        List<String> tagStrings = new ArrayList<>();
        for (Tag tag :
                tags) {
            tagStrings.add(tag.getTag());
        }

        return tagStrings;
    }

    @Override
    public List<String> getTags() {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT tag");
        List<Tag> tags = baseMapper.selectList(queryWrapper);

        List<String> tagStrings = new ArrayList<>();
        for (Tag tag :
                tags) {
            tagStrings.add(tag.getTag());
        }

        return tagStrings;
    }

    @Override
    public List<Long> getPostIdsByTag(String tag) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("post_id")
                .eq("tag", tag);
        List<Tag> tags = baseMapper.selectList(queryWrapper);

        List<Long> postIds = new ArrayList<>();
        for (Tag eachTag :
                tags) {
            postIds.add(eachTag.getPostId());
        }

        return postIds;
    }
}
