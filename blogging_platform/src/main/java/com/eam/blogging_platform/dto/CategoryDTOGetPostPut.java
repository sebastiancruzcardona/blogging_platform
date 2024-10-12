package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import com.eam.blogging_platform.entity.Category;

public class CategoryDTOGetPostPut {

    private long id;

    private String category;

    private String description;

    private LocalDateTime creationDate;

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    // Method to convert from Category entity to CategoryDTO
    public void convertToCategoryDTO(Category categoryEntity) {
        this.setId(categoryEntity.getId());
        this.setCategory(categoryEntity.getCategory());
        this.setDescription(categoryEntity.getDescription());
        this.setCreationDate(categoryEntity.getCreationDate());
    }
}
