package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.*;

public class CategoriesPostDTO {

    @Min(1)
    private long categoryId;

    @Min(1)
    private long postId;

    @NotNull
    private String relationshipType;

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

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }
}
