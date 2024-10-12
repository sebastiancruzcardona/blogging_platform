package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentDto {

    @NotBlank(message = "A comment must be provided")
    @Size(min = 2, max = 3000, message = "Not a valid comment")
    private String comment;

    @Min(1)
    private Long user_id;

    @Min(1)
    private Long post_id;

    public  String getComment() { return comment;}

    public void setComment(String comment) {this.comment = comment;}

    public Long getUser_id() { return user_id;}

    public void setUser_id(Long user_id) { this.user_id = user_id;}

    public Long getPost_id() {return post_id;}

    public void setPost_id(Long post_id) {this.post_id = post_id;}
}
