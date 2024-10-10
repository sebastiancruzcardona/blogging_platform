package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.entity.Post;
import com.eam.blogging_platform.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired //Singleton backwards for just one PostService instance
    private PostService postService;

    //This method refers to roleService.findAll() method. Brings out every RoleDTO stored in database table role as a List of roles
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAll();
    }

    //This method refers to postService.findById() method. Finds a specific post searching by id
    //If the post is found, maps the ResponseEntity and returns a 200 OK Status.
    //If there is not a post identified by that id, returns 404 Not Found Status
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable long id) {
        Optional<Post> post = postService.findById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //This method refers to postService.save() method. Saves a new post in database table post
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.save(post);
    }

    //This method refers to postService.findById() and postService.save() methods. Finds a specific post searching by id and updates it
    //If the post is found, sets the attributes to the post in edition, saves to update and returns a 200 OK Status.
    //If there is not a post identified by that id, returns 404 Not Found Status
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable long id, @RequestBody Post content) {
        Optional<Post> post = postService.findById(id);
        if (post.isPresent()) {
            Post updatedPost = post.get();
            updatedPost.setTitle(content.getTitle());
            updatedPost.setLikes(content.getLikes());
            return ResponseEntity.ok(postService.save(updatedPost));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //This method refers to postService.findById() and postService.deleteById() methods. Finds a specific post searching by id and deletes it
    //If the post is found, deletes it.
    //If there is not a post identified by that id, returns 404 Not Found Status
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        if (postService.findById(id).isPresent()) {
            postService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
