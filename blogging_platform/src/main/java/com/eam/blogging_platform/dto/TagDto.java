package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class TagDto {
    @NotBlank(message = "A tag must be provided")
    @Size(min = 2, max = 40, message = "Not a valid tag")
    @Pattern(regexp = "^[^0-9]*$", message = "Cannot contain numbers")
    private String tag;

    public String getTag(){ return tag;}

    public void setTag(String tag){this.tag = tag;}

}
