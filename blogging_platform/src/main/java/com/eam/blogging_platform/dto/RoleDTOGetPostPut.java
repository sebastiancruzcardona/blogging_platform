package com.eam.blogging_platform.dto;

import com.eam.blogging_platform.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleDTOGetPostPut {

    private Long id;

    private String role;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    //This method receives a Role and sets its attributes to the RoleDTOGetPostPut object
    public void convertToRoleDTO(Role role) {
        this.setId(role.getId());
        this.setRole(role.getRole());
        this.setDescription(role.getDescription());
    }
}
