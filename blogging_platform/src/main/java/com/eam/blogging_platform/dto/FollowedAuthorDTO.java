package com.eam.blogging_platform.dto;

import com.eam.blogging_platform.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class FollowedAuthorDTO {

    @Min(1)
    private Long followerId;

    @Min(1)
    private Long authorId;


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
}
