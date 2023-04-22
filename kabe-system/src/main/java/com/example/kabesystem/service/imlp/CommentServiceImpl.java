package com.example.kabesystem.service.imlp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kabesystem.mapper.CommentMapper;
import com.example.kabesystem.model.Comment;
import com.example.kabesystem.service.CommentService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public void createComment(Comment comment, Long userId) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        comment.setUserId(userId);
        comment.setCreateTime(timestamp);
        baseMapper.insert(comment);
    }

    @Override
    public Map<String, Object> getPostCommentsPaged(Long postId,
                                                    Integer pageIndex,
                                                    Integer pageSize) {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_type", "post")
                .eq("parent_id", postId);

        Page<Comment> page = new Page<>(pageIndex, pageSize);

        page = baseMapper.selectPage(page, queryWrapper);

        map.put("pages", page.getPages());
        map.put("total", page.getTotal());

        List<Comment> postComments = page.getRecords();

        List<Map<String, Object>> allComment = new ArrayList<>();

        for (Comment postComment :
                postComments) {
            Map<String, Object> childrenMap = new HashMap<>();
            QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.eq("parent_type", "comment")
                    .eq("parent_id", postComment.getId());
            childrenMap.put("comment", postComment);
            childrenMap.put("children", baseMapper.selectList(commentQueryWrapper));
            allComment.add(childrenMap);
        }

        map.put("postComments", allComment);
        return map;
    }
}
