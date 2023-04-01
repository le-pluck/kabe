package com.example.kabesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.kabesystem.model.test.Tested;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public interface TestedMapper extends BaseMapper<Tested> {
}
