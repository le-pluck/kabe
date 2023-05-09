package com.example.kabesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kabesystem.mapper.CommentFavorMapper;
import com.example.kabesystem.model.CommentFavor;
import com.example.kabesystem.service.CommentFavorService;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentFavorServiceImpl extends ServiceImpl<CommentFavorMapper, CommentFavor>
        implements CommentFavorService {

    @Override
    public Map<Long, Void> checkUserFavorOnComments(Long userId, List<Long> commentIds) {
        LambdaQueryWrapper<CommentFavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .select(CommentFavor::getCommentId)
                .eq(CommentFavor::getUserId, userId)
                .in(CommentFavor::getCommentId, commentIds);

        List<CommentFavor> commentFavors = baseMapper.selectList(lambdaQueryWrapper);

        Map<Long, Void> commentFavorMap = new HashMap<>(0);
        for (CommentFavor commentFavor : commentFavors) {
            commentFavorMap.put(commentFavor.getCommentId(), null);
        }

        return commentFavorMap;
    }

    @Override
    public void addFavor(Long userId, Long commentId, Integer favor) {
        baseMapper.insert(new CommentFavor(userId, commentId));
    }

    @Override
    public void cancelFavor(Long userId, Long commentId, Integer favor) {
        QueryWrapper<CommentFavor> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("comment_id", commentId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void removeAllFavorByCommentId(Long commentId) {
        LambdaQueryWrapper<CommentFavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CommentFavor::getCommentId, commentId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void removeAllFavorByCommentIds(List<Long> commentIds) {
        LambdaQueryWrapper<CommentFavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(CommentFavor::getCommentId, commentIds);
        baseMapper.delete(wrapper);
    }
}
