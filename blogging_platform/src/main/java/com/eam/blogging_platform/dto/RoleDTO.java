package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.*;

public class RoleDTO {

    @NotBlank(message = "A role must be provided")
    @Size(min = 3, max = 8, message = "Not a valid role")
    private String role;

    @NotBlank(message = "A description must be provided")
    @Size(min = 2, max = 250, message = "Not a valid description")
    private String description;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

