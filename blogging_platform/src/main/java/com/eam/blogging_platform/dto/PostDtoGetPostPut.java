package com.eam.blogging_platform.dto;

import com.eam.blogging_platform.entity.Comment;
import com.eam.blogging_platform.entity.Post;

import java.time.LocalDateTime;

public class PostDtoGetPostPut {

    private Long id;

    private String title;

    private String content;

    private Long userId;

    private Long statusId;

    private int likes;

    private int dislikes;

    private LocalDateTime creationDate;

    private LocalDateTime lastUpdateDate;

    private LocalDateTime publicationDate;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    // Convert to PostDTOGetPostPut from Post entity
    public void convertToPostDTO(Post post) {
        this.setId(post.getId());
        this.setTitle(post.getTitle());
        this.setContent(post.getContent());
        this.setUserId(post.getUser().getId());
        this.setStatusId(post.getStatus().getId());
        this.setLikes(post.getLikes());
        this.setDislikes(post.getDislikes());
        this.setCreationDate(post.getCreation_date());
        this.setLastUpdateDate(post.getLastUpdateDate());
        this.setPublicationDate(post.getPublication());
    }
}
