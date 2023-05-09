package com.example.kabesystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 申请
 *
 * @author pluck
 * @since 2023-05-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Submission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long submitterId;

    private String action;

    private String state;
}
