package com.eam.blogging_platform.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role", length = 8)
    private String role;

    @Column(name = "description", length = 250)
    private String description;

    public Role() {
        super();
    }

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role(long id, String role, String description) {
        this.id = id;
        this.role = role;
        this.description = description;
    }

    public Role(String role, String description) {
        this.role = role;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
