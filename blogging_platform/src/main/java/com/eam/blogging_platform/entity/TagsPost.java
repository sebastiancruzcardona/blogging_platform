package com.eam.blogging_platform.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tag_post")

public class TagsPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public TagsPost() {
    }

    public TagsPost(int id) {
        this.id = id;
    }
}


