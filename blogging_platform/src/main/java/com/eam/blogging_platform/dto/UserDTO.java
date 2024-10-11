package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.*;

public class UserDTO {

    @NotBlank(message = "A name must be provided")
    @Size(min = 1, max = 60, message = "Not a valid name, 1 character as minimum, 60 as maximum")
    private String username;

    @Email(message = "An email must be provided")
    @NotBlank(message = "An email must be provided")
    private String email;

    @NotBlank(message = "A password must be provided")
    //@Size(min = 6, max = 40, message = "Not a valid name, 6 character as minimum, 40 as maximum")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[@#$%^&+=!]).+$", message = "The password must contain at least one number and one special character")
    private String password;

    @Min(1)
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
}
