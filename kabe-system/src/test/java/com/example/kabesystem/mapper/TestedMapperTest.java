package com.example.kabesystem.mapper;

import com.example.kabesystem.model.test.Tested;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestedMapperTest {
    @Autowired
    private TestedMapper testedMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<Tested> testedList = testedMapper.selectList(null);
        System.out.println(testedList.size());
        assertEquals(1, testedList.size());
        testedList.forEach(System.out::println);
    }
}