package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.*;

public class CategoriesPostDTO {

    @Min(1)
    private long postId;

    @Min(1)
    private long categoryId;

    // Getters and Setters

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
