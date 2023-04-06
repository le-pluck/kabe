package com.example.kabesystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.kabesystem.mapper")
public class KabeSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(KabeSystemApplication.class, args);
    }

}
