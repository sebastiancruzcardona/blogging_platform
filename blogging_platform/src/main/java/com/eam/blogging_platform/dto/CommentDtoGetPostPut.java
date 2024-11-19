package com.eam.blogging_platform.dto;

import com.eam.blogging_platform.entity.Comment;
import com.eam.blogging_platform.entity.TagsPost;

import java.time.LocalDateTime;

public class CommentDtoGetPostPut {

    private Long id;
    private String comment;
    private Long userId;
    private Long postId;
    private LocalDateTime creation_date;
    private LocalDateTime last_update;
    private boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {return postId;}

    public void setPostId(Long postId) {this.postId = postId;}

    public LocalDateTime getCreation_date() {return creation_date;}

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public LocalDateTime getLast_update() {
        return last_update;
    }

    public void setLast_update(LocalDateTime last_update) {
        this.last_update = last_update;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void convertToCommentDTO(Comment comment) {
        this.setId(comment.getId());
        this.setComment(comment.getComment());
        this.setUserId(comment.getUser().getId());
        this.setPostId(comment.getPost().getId());
        this.setCreation_date(comment.getCreationDate());
        this.setLast_update(comment.getLastUpdateDate());
        this.setStatus(comment.getStatus());
    }
}


