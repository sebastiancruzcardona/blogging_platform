package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.*;

public class UserLoginDTO {

    @NotBlank(message = "A name must be provided")
    @Size(min = 1, max = 60, message = "Not a valid name, 1 character as minimum, 60 as maximum")
    private String username;

    @NotBlank(message = "A password must be provided")
    @Size(min = 4, max = 80, message = "Not a valid password, 4 character as minimum, 80 as maximum")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[@#$%^&+=!]).+$", message = "The password must contain at least one number and one special character")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
