package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class PostFindByContentDto {

    @NotBlank(message = "Content for search must be provided")
    @Size(min = 1, max = 30, message = "Not valid content, 1 character as minimum, 30 as maximum")
    @Pattern(regexp = "(?s)^(?!.*\\d{21})(?=.*[a-zA-Z]).*$", message = "Must contain at least one letter and cannot contain more than 20 consecutive numbers")
    private String content;

    // Getters and Setters

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
