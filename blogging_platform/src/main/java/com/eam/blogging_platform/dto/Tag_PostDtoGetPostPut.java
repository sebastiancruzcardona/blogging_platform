package com.eam.blogging_platform.dto;

import com.eam.blogging_platform.entity.FollowedAuthor;
import com.eam.blogging_platform.entity.TagsPost;

import java.time.LocalDateTime;

public class Tag_PostDtoGetPostPut {

    private Long id;

    private Long postId;

    private Long tagId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    //This method receives a TagPost and sets its attributes to the Tag_PostDtoGetPostPut object
    public void convertToTagPostDTO(TagsPost tagsPost) {
        this.setId(tagsPost.getId());
        this.setPostId(tagsPost.getPost().getId());
        this.setTagId(tagsPost.getTag().getId());

    }
}
