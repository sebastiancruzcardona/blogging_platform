package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.*;

public class CategoriesPostDTO {

    @Min(1)
    private Long categoryId;

    @Min(1)
    private Long postId;

    // Getters and Setters

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}
