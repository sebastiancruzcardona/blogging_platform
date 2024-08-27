package com.eam.blogging_platform.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "category", length = 60)
    private String category;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    public Category(){
        super();
    }

    public Category(long id, String category, String description, Date creationDate) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Category(String category, String description, Date creationDate) {
        this.category = category;
        this.description = description;
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

