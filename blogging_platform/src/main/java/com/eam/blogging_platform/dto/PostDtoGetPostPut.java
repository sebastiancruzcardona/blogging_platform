package com.eam.blogging_platform.dto;

import com.eam.blogging_platform.entity.Comment;
import com.eam.blogging_platform.entity.Post;

import java.time.LocalDateTime;

public class PostDtoGetPostPut {

    private Long id;
    private Long user_id;
    private String title;
    private String content;
    private Long status_id;
    private int likes;
    private int dislikes;
    private LocalDateTime creation_date;
    private LocalDateTime last_update;
    private LocalDateTime publication;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Long status_id) {
        this.status_id = status_id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
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

    public LocalDateTime getPublication() {
        return publication;
    }

    public void setPublication(LocalDateTime publication) {
        this.publication = publication;
    }

    public void convertToPostDTO(Post post) {
        this.setId(post.getId());
        this.setUser_id(post.getUser().getId());
        this.setTitle(post.getTitle());
        this.setContent(post.getContent());
        this.setStatus_id(post.getStatus().getId());
        this.setLikes(post.getLikes());
        this.setDislikes(post.getDislikes());
        this.setCreation_date(post.getCreation_date());
        this.setLast_update(post.getPublication());
    }
}
