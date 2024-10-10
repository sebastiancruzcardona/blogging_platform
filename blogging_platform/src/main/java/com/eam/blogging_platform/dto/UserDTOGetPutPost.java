package com.eam.blogging_platform.dto;

import com.eam.blogging_platform.entity.Role;
import com.eam.blogging_platform.entity.User;
import jakarta.validation.constraints.*;

public class UserDTOGetPutPost {

    private String username;

    private String email;

    private String password;

    private Long roleID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    //This method receives a User and sets its attributes to the UserDTOGetPostPut object
    public void convertToRoleDTO(User user) {
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setRoleID(user.getRole().getId());
    }

}
