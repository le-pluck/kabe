package com.example.kabesystem.controller;


import com.example.kabesystem.mapper.TestedMapper;
import com.example.kabesystem.model.test.Tested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tested")
public class TestedController {
    @Autowired
    TestedMapper testedMapper;

    @GetMapping("select_all")
    public List<Tested> select() {
        return testedMapper.selectList(null);
    };
}
