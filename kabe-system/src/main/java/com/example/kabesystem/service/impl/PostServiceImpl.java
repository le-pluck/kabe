package com.example.kabesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kabesystem.mapper.PostMapper;
import com.example.kabesystem.model.Post;
import com.example.kabesystem.service.CommentService;
import com.example.kabesystem.service.PostService;
import com.example.kabesystem.service.TagService;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    private final CommentService commentService;
    private final TagService tagService;

    public PostServiceImpl(CommentService commentService, TagService tagService) {
        this.commentService = commentService;
        this.tagService = tagService;
    }

    @Override
    public Post selectById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Long createPost(Post post, Long posterId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
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
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(
                        Post::getId,
                        Post::getPosterId,
                        Post::getTitle,
                        Post::getSubtitle,
                        Post::getReaction,
                        Post::getStar,
                        Post::getCreateTime,
                        Post::getUpdateTime)
                .orderByDesc(Post::getCreateTime);

        Page<Post> page = new Page<>(pageIndex, pageSize);

        page = baseMapper.selectPage(page, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("pages", page.getPages());
        map.put("total", page.getTotal());
        map.put("postPreviews", page.getRecords());
        return map;
    }

    @Override
    public Map<String, Object> getPostPreviewsLatestPagedByPosterId(
            Long posterId, Integer pageIndex, Integer pageSize) {
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper
                .select(
                        Post::getId,
                        Post::getPosterId,
                        Post::getTitle,
                        Post::getSubtitle,
                        Post::getReaction,
                        Post::getStar,
                        Post::getCreateTime,
                        Post::getUpdateTime)
                .eq(Post::getPosterId, posterId)
                .orderByDesc(Post::getCreateTime);

        Page<Post> page = new Page<>(pageIndex, pageSize);

        page = baseMapper.selectPage(page, queryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("pages", page.getPages());
        map.put("total", page.getTotal());
        map.put("postPreviews", page.getRecords());
        return map;
    }

    @Override
    public Map<String, Object> getPostPreviewsLatestPagedByTagName(
            String tagName, Integer pageIndex, Integer pageSize) {

        List<Long> postIds = tagService.getPostIdsByTagName(tagName);

        System.out.println("");
        System.out.println("=================");
        System.out.println("标签 " + tagName);
        System.out.println(postIds);
        System.out.println("=================");
        System.out.println("");

        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(
                        Post::getId,
                        Post::getPosterId,
                        Post::getTitle,
                        Post::getSubtitle,
                        Post::getReaction,
                        Post::getStar,
                        Post::getCreateTime,
                        Post::getUpdateTime)
                .in(Post::getId, postIds)
                .orderByDesc(Post::getCreateTime);

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

    @Override
    public boolean deletePostById(Long postId, Long userId) {
        Post post = baseMapper.selectById(postId);
        if (!post.getPosterId().equals(userId)) {
            return false;
        }

        tagService.removeTagsByPostId(postId);

        List<Long> commentIds = commentService.getAllCommentIdsByPostId(postId);

        if (commentIds.size() == 0) {
            return baseMapper.deleteById(postId) == 1;
        }

        return commentService.removeAllCommentByCommentIds(commentIds)
                && baseMapper.deleteById(postId) == 1;
    }

    @Override
    public boolean deletePostByIdAdmin(Long postId) {
        List<Long> commentIds = commentService.getAllCommentIdsByPostId(postId);

        if (commentIds.size() == 0) {
            return baseMapper.deleteById(postId) == 1;
        }

        return commentService.removeAllCommentByCommentIds(commentIds)
                && baseMapper.deleteById(postId) == 1;
    }
}
