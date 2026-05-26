package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.*;

public class UserLoginDTO {

    @NotBlank(message = "A name must be provided")
    @Size(min = 3, max = 40, message = "Not a valid name, 3 character as minimum, 40 as maximum")
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9_.-]+$", message = "Username must contain at least one letter and can only contain letters, numbers, dots, dashes and underscores")
    private String username;

    @NotBlank(message = "A password must be provided")
    @Size(min = 8, max = 80, message = "Not a valid password, 8 character as minimum, 80 as maximum")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).+$", message = "The password must contain at least one lowercase letter, one uppercase letter, one number and one special character")
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
