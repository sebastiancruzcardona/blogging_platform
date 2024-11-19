package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentUpdateDto {

    @NotBlank(message = "A comment must be provided")
    @Size(min = 2, max = 3000, message = "Not a valid comment")
    private String comment;

    public  String getComment() { return comment;}

    public void setComment(String comment) {this.comment = comment;}
}
