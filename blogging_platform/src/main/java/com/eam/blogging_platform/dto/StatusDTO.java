package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.*;

public class StatusDTO {

    @NotBlank(message = "A status must be provided")
    @Size(min = 1, max = 15, message = "Not a valid status, 1 character as minimum, 15 as maximum")
    private String status;

    // Getters and Setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
