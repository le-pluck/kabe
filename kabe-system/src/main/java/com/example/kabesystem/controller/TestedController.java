package com.example.kabesystem.controller;


import com.example.kabesystem.mapper.TestedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tested")
public class TestedController {
//    @Autowired
//    TestedMapper testedMapper;

    @GetMapping("select")
    public int select() {
        System.out.println("\n\n ============== BABY ============== \n\n");
//        return testedMapper.selectList(null).size();
        return 1;
    };
}
