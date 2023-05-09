package com.example.kabesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.kabesystem.model.Post;
import com.github.yulichang.base.MPJBaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMapper extends MPJBaseMapper<Post> {
}
