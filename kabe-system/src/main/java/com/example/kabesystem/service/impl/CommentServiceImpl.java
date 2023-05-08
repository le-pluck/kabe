package com.example.kabesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kabesystem.dto.comment.CommentResponseDTO;
import com.example.kabesystem.mapper.CommentMapper;
import com.example.kabesystem.model.Comment;
import com.example.kabesystem.service.CommentFavorService;
import com.example.kabesystem.service.CommentService;
import com.github.yulichang.query.MPJQueryWrapper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final CommentFavorService commentFavorService;

    public CommentServiceImpl(CommentFavorService commentFavorService) {
        this.commentFavorService = commentFavorService;
    }


    @Override
    public boolean createComment(Comment comment, Long userId) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        comment.setUserId(userId);
        comment.setCreateTime(timestamp);
        return baseMapper.insert(comment) != 0;
    }

    @Override
    public Map<String, Object> getPostCommentsSortedPaged(
            Long postId,
            Integer pageIndex,
            Integer pageSize,
            String sortingCriteria,
            Long userId
    ) {

        Map<String, Object> resultMap = new HashMap<>();
        List<Long> commentIds = new ArrayList<>();

        MPJQueryWrapper<Comment> postCommentQueryWrapper = new MPJQueryWrapper<>();
        postCommentQueryWrapper
                .select("""
                        t.*,
                        ua.nickname AS nickname,
                        ua.avatar AS avatar
                        """)
                .eq("parent_type", "post")
                .eq("storage_parent_id", postId)
                .leftJoin("user_account ua ON t.user_id = ua.id");
        switch (sortingCriteria) {
            case "latest":
                postCommentQueryWrapper.orderByDesc("create_time");
            default:
                // 处理异常
        }

        Page<Map<String, Object>> page = new Page<>(pageIndex, pageSize);

        Page<Map<String, Object>> mapsPage = baseMapper.selectMapsPage(page, postCommentQueryWrapper);

        resultMap.put("pages", mapsPage.getPages());
        resultMap.put("total", mapsPage.getTotal());

        List<Map<String, Object>> postCommentMaps = mapsPage.getRecords();
        List<CommentResponseDTO> postCommentResponseDTOList = new ArrayList<>();
        for (Map<String, Object> map : postCommentMaps) {
            CommentResponseDTO postCommentResponseDTO = new CommentResponseDTO();
            try {
                BeanUtils.populate(postCommentResponseDTO, map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            postCommentResponseDTOList.add(postCommentResponseDTO);
            commentIds.add(postCommentResponseDTO.getId());
        }

        List<Map<String, Object>> allComment = new ArrayList<>();

        for (CommentResponseDTO postCommentResponseDTO :
                postCommentResponseDTOList) {
            Map<String, Object> childrenMap = new HashMap<>();
            MPJQueryWrapper<Comment> wrapper = new MPJQueryWrapper<>();
            wrapper
                    .select("""
                            t.*,
                            child_ua.nickname as nickname,
                            child_ua.avatar as avatar,
                            parent_ua.nickname as parent_nickname
                            """)
                    .eq("t.parent_type", "comment")
                    .eq("t.storage_parent_id", postCommentResponseDTO.getId())
                    .leftJoin("user_account child_ua on t.user_id = child_ua.id")
                    .leftJoin("comment parent_c on t.logical_parent_id = parent_c.id")
                    .leftJoin("user_account parent_ua on parent_c.user_id = parent_ua.id");

            List<Map<String, Object>> childCommentMaps = baseMapper.selectMaps(wrapper);
            List<CommentResponseDTO> childCommentResponseDTOList = new ArrayList<>();
            for (Map<String, Object> map : childCommentMaps) {
                CommentResponseDTO childCommentResponseDTO = new CommentResponseDTO();
                try {
                    BeanUtils.populate(childCommentResponseDTO, map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                childCommentResponseDTOList.add(childCommentResponseDTO);
                commentIds.add(childCommentResponseDTO.getId());
            }
            childrenMap.put("comment", postCommentResponseDTO);
            childrenMap.put("children", childCommentResponseDTOList);
            allComment.add(childrenMap);
        }

        Map<Long, Void> checkMap = commentFavorService.checkUserFavorOnComments(userId, commentIds);

        for (Map<String, Object> postCommentMap : allComment) {
            CommentResponseDTO postCommentResponseDTO = (CommentResponseDTO) postCommentMap.get("comment");
            postCommentResponseDTO.setFavored(checkMap.containsKey(postCommentResponseDTO.getId()));

            // WARNING: 下面的警告我暂时不知如何很好地解决
            // 警告内容: 未检查的转换: 'java.lang.Object' 转换为 'java.util.List<com.example.kabesystem.dto.comment.CommentResponseDTO>'
            List<CommentResponseDTO> children = (List<CommentResponseDTO>) postCommentMap.get("children");
            for (CommentResponseDTO child : children) {
                child.setFavored(checkMap.containsKey(child.getId()));
            }
        }

        resultMap.put("postComments", allComment);
        return resultMap;
    }

    @Override
    public void addFavor(Long userId, Long commentId, Integer favor) {
        commentFavorService.addFavor(userId, commentId, favor);
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setFavor(favor);
        baseMapper.updateById(comment);
    }

    @Override
    public void cancelFavor(Long userId, Long commentId, Integer favor) {
        commentFavorService.cancelFavor(userId, commentId, favor);
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setFavor(favor);
        baseMapper.updateById(comment);
    }

    @Override
    public boolean removeComment(Long commentId, Long userId) {
        // userId 用于验证，确保 commentId 对应的评论是 userId 对应的用户发布的

        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper
            .eq(Comment::getId, commentId)
            .eq(Comment::getUserId, userId);

        boolean success = baseMapper.delete(wrapper) == 1;

        if (success) {
            commentFavorService.removeComment(commentId);
        }

        return success;
    }

    @Override
    public boolean removeCommentAdmin(Long commentId) {
        commentFavorService.removeComment(commentId);
        return baseMapper.deleteById(commentId) == 1;
    }
}
