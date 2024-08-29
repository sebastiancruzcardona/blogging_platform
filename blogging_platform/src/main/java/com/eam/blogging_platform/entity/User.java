package com.eam.blogging_platform.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", length = 60)
    private String username;

    @Column(name = "email", length = 60, unique = true)
    private String email;

    @Column(name = "password", length = 30)
    private String password;

    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<FollowedAuthors> followers;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<FollowedAuthors> followed_authors; //is it possible to delete author or follower?

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    public User() {
        super();
    }

    public User(long id, String username, String email, String password, Date creationDate, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.creationDate = creationDate;
        this.role = role;
    }

    public User(String username, String email, String password, Date creationDate, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.creationDate = creationDate;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<FollowedAuthors> getFollowers() {
        return followers;
    }

    public List<FollowedAuthors> getFollowed_authors() {
        return followed_authors;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
