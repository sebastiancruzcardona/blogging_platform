package com.eam.blogging_platform.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

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

    @Column(name = "content", length = 6000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(name = "likes")
    private int likes;

    @Column(name = "dislikes")
    private int dislikes;

    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column(name = "lastUpdateDate")
    @Temporal(TemporalType.DATE)
    private Date lastUpdateDate;

    @Column(name = "publication")
    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<CategoriesPost> categoriesPosts;

    public Post() {
        super();
    }

    public Post(long id, User user, String title, String content, int likes, int dislikes, Date creation_date, Date last_update_date, Date publicationDate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.dislikes = dislikes;
        this.creationDate = creation_date;
        this.lastUpdateDate = last_update_date;
        this.publicationDate = publicationDate;
    }

    public Post(User user, String title, String content, int likes, int dislikes, Date creation_date, Date last_update_date, Date publicationDate) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.dislikes = dislikes;
        this.creationDate = creation_date;
        this.lastUpdateDate = last_update_date;
        this.publicationDate = publicationDate;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public Date getCreation_date() {
        return creationDate;
    }

    public void setCreation_date(Date creation_date) {
        this.creationDate = creation_date;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getPublication() {
        return publicationDate;
    }

    public void setPublication(Date publication) {
        this.publicationDate     = publication;
    }
}

