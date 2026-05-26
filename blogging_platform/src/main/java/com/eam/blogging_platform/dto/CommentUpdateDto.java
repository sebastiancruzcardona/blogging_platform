package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class CommentUpdateDto {

    @NotBlank(message = "A comment must be provided")
    @Size(min = 2, max = 3000, message = "Not a valid comment")
    @Pattern(regexp = "(?s)^(?!.*\\d{21})(?=.*[a-zA-Z]).*$", message = "Must contain at least one letter and cannot contain more than 20 consecutive numbers")
    private String comment;

    public  String getComment() { return comment;}

    public void setComment(String comment) {this.comment = comment;}
}
