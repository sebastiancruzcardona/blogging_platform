package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class CategoryDTO {

    @NotBlank(message = "A category must be provided")
    @Size(min = 1, max = 60, message = "Not a valid category, 1 character as minimum, 60 as maximum")
    @Pattern(regexp = "^[^0-9]*$", message = "Cannot contain numbers")
    private String category;

    @Size(max = 250, message = "Description must be up to 250 characters")
    @Pattern(regexp = "(?s)^(?!.*\\d{21})(?=.*[a-zA-Z]).*$", message = "Must contain at least one letter and cannot contain more than 20 consecutive numbers")
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
