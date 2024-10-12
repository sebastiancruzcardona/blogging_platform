package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostDto {

    @Min(1)
    private Long user_id;

    @NotBlank(message = "A post must be provided")
    @Size(min = 2, max = 150, message = "Not a valid post")
    private String title;

    @NotBlank(message = "A post must be provided")
    @Size(min = 2, max = 100, message = "Not a valid post")
    private String content;

    @Min(1)
    private Long status_id;

    public Long getUser_id() {return user_id;}

    public void setUser_id(Long user_id) {this.user_id = user_id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

    public Long getStatus_id() {return status_id;}

    public void setStatus_id(Long status_id) {this.status_id = status_id;}
}
