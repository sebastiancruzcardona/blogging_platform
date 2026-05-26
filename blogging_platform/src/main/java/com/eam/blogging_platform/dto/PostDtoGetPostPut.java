package com.eam.blogging_platform.dto;

import com.eam.blogging_platform.entity.Comment;
import com.eam.blogging_platform.entity.Post;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class PostDtoGetPostPut {

    private Long id;

    @NotBlank(message = "A title must be provided")
    @Size(min = 1, max = 150, message = "Not a valid title, 1 character as minimum, 150 as maximum")
    @Pattern(regexp = "(?s)^(?!.*\\d{21})(?=.*[a-zA-Z]).*$", message = "Must contain at least one letter and cannot contain more than 20 consecutive numbers")
    private String title;

    @NotBlank(message = "Content must be provided")
    @Size(min = 1, max = 6000, message = "Not valid content, 1 character as minimum, 6000 as maximum")
    @Pattern(regexp = "(?s)^(?!.*\\d{21})(?=.*[a-zA-Z]).*$", message = "Must contain at least one letter and cannot contain more than 20 consecutive numbers")
    private String content;

    @NotNull(message = "User ID must be provided")
    @Min(value = 1, message = "User ID must be greater than or equal to 1")
    private Long userId;

    @NotNull(message = "Status ID must be provided")
    @Min(value = 1, message = "Status ID must be greater than or equal to 1")
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
