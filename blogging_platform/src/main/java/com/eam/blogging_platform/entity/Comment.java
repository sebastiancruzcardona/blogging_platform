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

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    public Comment() {
        super();
    }

    public Comment(long id, String comment, User user, Post post, LocalDateTime creationDate, LocalDateTime lastUpdate) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.post = post;
        this.creationDate = creationDate;
        this.lastUpdate = lastUpdate;
    }

    public Comment(String comment, User user, Post post, LocalDateTime creationDate, LocalDateTime lastUpdate) {
        this.comment = comment;
        this.user = user;
        this.post = post;
        this.creationDate = creationDate;
        this.lastUpdate = lastUpdate;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}



