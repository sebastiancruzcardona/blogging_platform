package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.Min;

public class Tag_PostDto {

    @Min(1)
    private Long postId;

    @Min(1)
    private Long tagId;

    public @Min(1) Long getPostId() {
        return postId;
    }

    public void setPostId(@Min(1) Long postId) {
        this.postId = postId;
    }

    public @Min(1) Long getTagId() {
        return tagId;
    }

    public void setTagId(@Min(1) Long tagId) {
        this.tagId = tagId;
    }
}
