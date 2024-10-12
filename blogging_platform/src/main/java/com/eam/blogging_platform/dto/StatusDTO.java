package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.*;

public class StatusDTO {

    @Min(1)
    private long id;

    @NotBlank(message = "Debe ingresar un estado")
    @Size(min = 1, max = 15, message = "El estado debe ser entre 1 y 15 caracteres")
    private String status;

    // Getters and Setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
