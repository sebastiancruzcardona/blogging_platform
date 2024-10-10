package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TagDto {
    @NotBlank(message = "A tag must be provided")
    private String tag;

    public String getTag(){ return tag;}

    public void setTag(String tag){this.tag = tag;}

}
