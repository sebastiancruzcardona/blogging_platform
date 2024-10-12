package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class CategoryDTO {

    @Min(1)
    private long id;

    @NotBlank(message = "Category must be provided")
    @Size(min = 1, max = 60, message = "Category must be between 1 and 60 characters")
    private String category;

    @Size(max = 250, message = "Description must be up to 250 characters")
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
}
