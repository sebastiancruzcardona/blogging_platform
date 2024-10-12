package com.eam.blogging_platform.dto;

import com.eam.blogging_platform.entity.Comment;
import com.eam.blogging_platform.entity.TagsPost;

import java.time.LocalDateTime;

public class CommentDtoGetPostPut {

    private Long id;
    private String comment;
    private Long user_id;
    private Long post_id;
    private LocalDateTime creation_date;
    private LocalDateTime last_update;

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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public LocalDateTime getLast_update() {
        return last_update;
    }

    public void setLast_update(LocalDateTime last_update) {
        this.last_update = last_update;
    }

    public void convertToCommentDTO(Comment comment) {
        this.setId(comment.getId());
        this.setComment(comment.getComment());
        this.setUser_id(comment.getUser().getId());
        this.setPost_id(comment.getPost().getId());
        this.setCreation_date(comment.getCreationDate());
        this.setLast_update(comment.getLastUpdateDate());
    }
}


