package com.eam.blogging_platform.dto;

import com.eam.blogging_platform.entity.FollowedAuthor;

import java.time.LocalDateTime;

public class FollowedAuthorDTOGetPostPut {

    private Long id;

    private Long followerId;

    private Long authorId;

    private LocalDateTime creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    //This method receives a FollowedAuthor and sets its attributes to the FollowedAuthorDTOGetPostPut object
    public void convertToTagPostDTO(FollowedAuthor followedAuthor) {
        this.setId(followedAuthor.getId());
        this.setCreationDate(followedAuthor.getCreationDate());
        this.setAuthorId(followedAuthor.getAuthor().getId());
        this.setFollowerId(followedAuthor.getFollower().getId());
    }
}
