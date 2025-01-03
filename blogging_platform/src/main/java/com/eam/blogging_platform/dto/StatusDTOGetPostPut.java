package com.eam.blogging_platform.dto;

import com.eam.blogging_platform.entity.Status;
import jakarta.validation.constraints.*;

public class StatusDTOGetPostPut {

    private long id;

    private String status;

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Method to convert from Status entity to StatusDTO
    public void convertToStatusDTO(Status statusEntity) {
        this.setId(statusEntity.getId());
        this.setStatus(statusEntity.getStatus());
    }
}

