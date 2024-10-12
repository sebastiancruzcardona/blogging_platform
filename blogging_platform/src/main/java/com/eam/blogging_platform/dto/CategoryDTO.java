package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class CategoryDTO {

    @NotBlank(message = "Debe ingresar una categoria")
    @Size(min = 1, max = 60, message = "La categoria debe ser entre 1 y 60 caracteres")
    private String category;

    @Size(max = 250, message = "La descripción debe ser menor a 250 caracteres")
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
