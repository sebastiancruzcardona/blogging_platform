package com.eam.blogging_platform.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tag", length = 40)
    private String tag;

    @Column(name = "creationDate")
    private Date creationDate;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<TagsPost> tagsPosts;

    public Tag() {
        super();
    }

    public Tag(long id, String tag, Date creationDate, List<TagsPost> tagsPosts) {
        this.id = id;
        this.tag = tag;
        this.creationDate = creationDate;
        this.tagsPosts = tagsPosts;
    }

    public Tag(String tag, Date creationDate) {
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<TagsPost> getTagsPosts() {
        return tagsPosts;
    }
}

