package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class PostUpdateDTO {

    @NotBlank(message = "A title must be provided")
    @Size(min = 2, max = 150, message = "Not a valid title")
    @Pattern(regexp = "(?s)^(?!.*\\d{21})(?=.*[a-zA-Z]).*$", message = "Must contain at least one letter and cannot contain more than 20 consecutive numbers")
    private String title;

    @NotBlank(message = "Some content must be provided")
    @Size(min = 2, max = 6000, message = "Not valid content")
    @Pattern(regexp = "(?s)^(?!.*\\d{21})(?=.*[a-zA-Z]).*$", message = "Must contain at least one letter and cannot contain more than 20 consecutive numbers")
    private String content;

    @Min(1)
    private Long status_id;

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

    public Long getStatus_id() {return status_id;}

    public void setStatus_id(Long status_id) {this.status_id = status_id;}
}
