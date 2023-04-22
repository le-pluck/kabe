package com.example.kabesystem.model;

import lombok.Data;

@Data
public class Tag {
    private Long postId;
    private String name;
    private String icon;

    public Tag() {
    }

    public Tag(Long postId, String name, String icon) {
        this.postId = postId;
        this.name = name;
        this.icon = icon;
    }
}
