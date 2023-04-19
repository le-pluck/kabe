package com.example.kabesystem.model;

import lombok.Data;

@Data
public class Tag {
    private Long postId;
    private String tag;

    public Tag() {
    }

    public Tag(Long postId, String tagName) {
        this.postId = postId;
        this.tag = tag;
    }
}
