package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class CategoryDTO {

    @NotBlank(message = "A category must be provided")
    @Size(min = 1, max = 60, message = "Not a valid category, 1 character as minimum, 60 as maximum")
    private String category;

    @Size(max = 250, message = "Description must be up to 250 characters")
    private String description;

    private LocalDateTime creationDate;

    // Getters and Setters

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
