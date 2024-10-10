package com.eam.blogging_platform.dto;

import com.eam.blogging_platform.entity.User;

import java.time.LocalDateTime;

public class UserDTOGetPostPut {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Long roleID;

    private LocalDateTime creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    //This method receives a User and sets its attributes to the UserDTOGetPostPut object
    public void convertToUserDTO(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setRoleID(user.getRole().getId());
        this.setCreationDate(user.getCreationDate());
    }

}
