package com.eam.blogging_platform.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", length = 60, unique = true)
    private String username;

    @Column(name = "email", length = 60, unique = true)
    private String email;

    @Column(name = "password", length = 80)
    private String password;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<FollowedAuthor> followers;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<FollowedAuthor> followed_authors; //is it possible to deleteById author or follower?

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    public User() {
        super();
    }

    public User(long id, String username, String email, String password, LocalDateTime creationDate, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.creationDate = creationDate;
        this.role = role;
    }

    public User(String username, String email, String password, LocalDateTime creationDate, Role role) {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<FollowedAuthor> getFollowers() {
        return followers;
    }

    public List<FollowedAuthor> getFollowed_authors() {
        return followed_authors;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Post> getPosts() {
        return posts;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
