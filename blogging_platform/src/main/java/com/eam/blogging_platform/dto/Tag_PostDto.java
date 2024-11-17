package com.eam.blogging_platform.dto;

import jakarta.validation.constraints.Min;

public class Tag_PostDto {

    @Min(1)
    private Long postId;

    @Min(1)
    private Long tagId;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
