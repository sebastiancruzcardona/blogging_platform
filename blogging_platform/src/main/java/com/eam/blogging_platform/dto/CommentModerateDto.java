package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.NotNull;

public class CommentModerateDto {

    @NotNull
    private boolean status;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
