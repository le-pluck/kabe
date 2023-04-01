package com.example.kabesystem.model.test;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Tested {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String message;
}
