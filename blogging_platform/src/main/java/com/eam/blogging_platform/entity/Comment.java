package com.eam.blogging_platform.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "comment", length = 3000)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @Column(name = "status")
    private boolean status;

    public Comment() {
        super();
    }

    public Comment(long id, String comment, User user, Post post, LocalDateTime creationDate, LocalDateTime lastUpdateDate, boolean status) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.post = post;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.status = status;
    }

    public Comment(String comment, User user, Post post, LocalDateTime creationDate, LocalDateTime lastUpdateDate, boolean status) {
        this.comment = comment;
        this.user = user;
        this.post = post;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}



