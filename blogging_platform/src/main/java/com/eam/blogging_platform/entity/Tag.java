package com.eam.blogging_platform.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tag", length = 40)
    private String tag;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<TagsPost> tagsPosts;

    public Tag() {
        super();
    }

    public Tag(long id, String tag, LocalDateTime creationDate) {
        this.id = id;
        this.tag = tag;
        this.creationDate = creationDate;

    }

    public Tag(String tag, LocalDateTime creationDate) {
        this.tag = tag;
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<TagsPost> getTagsPosts() {
        return tagsPosts;
    }
}

