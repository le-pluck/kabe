package com.example.kabesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.kabesystem.model.Comment;
import com.github.yulichang.base.MPJBaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentMapper extends MPJBaseMapper<Comment> {
}
