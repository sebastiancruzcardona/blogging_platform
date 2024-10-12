package com.eam.blogging_platform.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories_post")
public class CategoriesPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public CategoriesPost() {
        super();
    }

    public CategoriesPost(Long id, Post post, Category category) {
        this.id = id;
        this.post = post;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

