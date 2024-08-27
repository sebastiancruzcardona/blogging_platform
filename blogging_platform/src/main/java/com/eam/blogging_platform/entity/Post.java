package com.eam.blogging_platform.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title", length = 150)
    private String title;

    @Column(name = "content")
    private String content;

    /*@ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;*/

    @Column(name = "likes")
    private int likes;

    @Column(name = "dislikes")
    private int dislikes;

    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creation_date;

    @Column(name = "last_update_date")
    @Temporal(TemporalType.DATE)
    private Date last_update_date;

    @Column(name = "publication")
    private Date publication;

    public Post() {
        super();
    }

    public Post(long id, User user, String title, String content, int likes, int dislikes, Date creation_date, Date last_update_date, Date publication) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.dislikes = dislikes;
        this.creation_date = creation_date;
        this.last_update_date = last_update_date;
        this.publication = publication;
    }

    public Post(User user, String title, String content, int likes, int dislikes, Date creation_date, Date last_update_date, Date publication) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.dislikes = dislikes;
        this.creation_date = creation_date;
        this.last_update_date = last_update_date;
        this.publication = publication;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    /*public Status getStatus() {
        return status;
    }*/

    /*public void setStatus(Status status) {
        this.status = status;
    }*/

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

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Date getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(Date last_update_date) {
        this.last_update_date = last_update_date;
    }

    public Date getPublication() {
        return publication;
    }

    public void setPublication(Date publication) {
        this.publication = publication;
    }
}

