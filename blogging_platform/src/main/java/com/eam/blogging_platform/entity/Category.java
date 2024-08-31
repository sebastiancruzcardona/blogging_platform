package com.eam.blogging_platform.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<CategoriesPost> categoriesPosts;

    public Category(){
        super();
    }

    public Category(long id, String category, String description, LocalDateTime creationDate) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Category(String category, String description, LocalDateTime creationDate) {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<CategoriesPost> getCategoriesPosts() {
        return categoriesPosts;
    }
}

