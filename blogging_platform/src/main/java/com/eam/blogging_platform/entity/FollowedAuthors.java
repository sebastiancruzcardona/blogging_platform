package com.eam.blogging_platform.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "followed_authors")
public class FollowedAuthors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public FollowedAuthors() {
        super();
    }

    public FollowedAuthors(long id, Date creationDate, User user, User author) {
        this.id = id;
        this.creationDate = creationDate;
        this.user = user;
        this.author = author;
    }

    public FollowedAuthors(Date creationDate, User user, User author) {
        this.creationDate = creationDate;
        this.user = user;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
