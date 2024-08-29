package com.eam.blogging_platform.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "status", length = 15)
    private String status;

    @OneToMany(mappedBy = "status")
    private List<Post> posts;

    public Status() {
        super();
    }

    public Status(int id, String status) {
        this.id = id;
        this.status = status;
    }
    
    public Status(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
