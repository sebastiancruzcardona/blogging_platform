package com.eam.blogging_platform.dto;

import com.eam.blogging_platform.entity.CategoriesPost;
import jakarta.validation.constraints.*;

public class CategoriesPostDTOGetPostPut {

    private Long id;

    private Long postId;

    private Long categoryId;

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    // Method to convert from CategoriesPost entity to CategoriesPostDTO
    public void convertToCategoriesPostDTO(CategoriesPost categoriesPostEntity) {
        this.setId(categoriesPostEntity.getId());
        this.setPostId(categoriesPostEntity.getPost().getId());
        this.setCategoryId(categoriesPostEntity.getCategory().getId());
    }
}

