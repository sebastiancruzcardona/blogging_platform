package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.entity.Post;
import com.eam.blogging_platform.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostController postController;
    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postController.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostId(@PathVariable long id) {
        Optional<Post> post = postService.findById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.save(post);
    }

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
