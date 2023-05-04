package com.example.kabesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.kabesystem.dto.comment.CommentResponseDTO;
import com.example.kabesystem.mapper.CommentMapper;
import com.example.kabesystem.model.Comment;
import com.example.kabesystem.service.CommentService;
import com.github.yulichang.query.MPJQueryWrapper;
import org.apache.commons.beanutils.BeanUtils;
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
        Map<String, Object> resultMap = new HashMap<>();

        MPJQueryWrapper<Comment> postCommentQueryWrapper = new MPJQueryWrapper<>();
        postCommentQueryWrapper
                .select("""
                        t.*,
                        ua.nickname as nickname,
                        ua.avatar as avatar
                        """)
                .eq("parent_type", "post")
                .eq("storage_parent_id", postId)
                .leftJoin("user_account ua on t.user_id = ua.id");

        Page<Map<String, Object>> page = new Page<>(pageIndex, pageSize);

        Page<Map<String, Object>> mapsPage = baseMapper.selectMapsPage(page, postCommentQueryWrapper);

        resultMap.put("pages", mapsPage.getPages());
        resultMap.put("total", mapsPage.getTotal());

        List<Map<String, Object>> postCommentMaps = mapsPage.getRecords();
        List<CommentResponseDTO> postCommentResponseDTOList = new ArrayList<>();
        for (Map<String, Object> map : postCommentMaps) {
            CommentResponseDTO postCommentResponseDTO = new CommentResponseDTO();
            System.out.println("postCommentMaps -> postCommentResponseDTOList | map:" + map.toString());
            try {
                BeanUtils.populate(postCommentResponseDTO, map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            postCommentResponseDTOList.add(postCommentResponseDTO);
            System.out.println("postCommentMaps -> postCommentResponseDTOList | model:" + postCommentResponseDTO.toString());
        }

        List<Map<String, Object>> allComment = new ArrayList<>();

        for (CommentResponseDTO postCommentResponseDTO :
                postCommentResponseDTOList) {
            Map<String, Object> childrenMap = new HashMap<>();
            MPJQueryWrapper<Comment> wrapper  = new MPJQueryWrapper<>();
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
             for ( Map<String, Object> map : childCommentMaps) {
                 CommentResponseDTO childCommentResponseDTO = new CommentResponseDTO();
                 try {
                     BeanUtils.populate(childCommentResponseDTO, map);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
                 childCommentResponseDTOList.add(childCommentResponseDTO);
                 System.out.println("childCommentMaps -> childCommentResponseDTOList:" + childCommentResponseDTO.toString());
             }
            childrenMap.put("comment", postCommentResponseDTO);
            childrenMap.put("children", childCommentResponseDTOList);
            allComment.add(childrenMap);
        }

        resultMap.put("postComments", allComment);
        return resultMap;
    }
}
