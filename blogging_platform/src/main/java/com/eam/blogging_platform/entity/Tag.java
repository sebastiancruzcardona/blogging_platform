package com.eam.blogging_platform.entity;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tag", length = 40)
    private String tag;

    @Column(name = "creation_date")
    private Date creation_date;

    @OneToMany(mappedBy = "tag_post")
    List<TagPost> tags;

    public Tag() {
    }

    public Tag(int id, String tag, Date creation_date) {
        this.id = id;
        this.tag = tag;
        this.creation_date = creation_date;
    }

    public Tag(String tag, Date creation_date) {
        this.tag = tag;
        this.creation_date = creation_date;
    }

    public int getId() {
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

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }
}

