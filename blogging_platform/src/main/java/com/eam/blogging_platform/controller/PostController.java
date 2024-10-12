package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.dto.PostDto;
import com.eam.blogging_platform.dto.PostDtoGetPostPut;
import com.eam.blogging_platform.entity.Post;
import com.eam.blogging_platform.service.PostService;
import jakarta.validation.Valid;
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

    //This method refers to postService.findAll() method. Brings out every post stored in database table post as a List of PostDto
    @GetMapping
    public List<PostDtoGetPostPut> getAllPosts() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    //This method calls the findById method from postService that returns an Optional
    //Then, tries to map the Optional postDtoGetPostPut by using the .ok() function from ResponseEntity, for this the post has to be present
    //If the optional is empty, executes the orElseGet() implementing a ResponseEntity.notFound().build()
    //It is equivalent to writing:
        /*if(postDtoGetPostPut.isPresent()){
          return ResponseEntity.ok(postDtoGetPostPut);
        else{
          return ResponseEntity.notFound().build();
        }*/
    public ResponseEntity<PostDtoGetPostPut> getPostById(@PathVariable long id) {
        Optional<PostDtoGetPostPut> postDtoGetPostPut = postService.findById(id);
        return postDtoGetPostPut.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //This method refers to postService.save() method. Saves a new post in database table posts
    @PostMapping
    public PostDtoGetPostPut createPost(@Valid @RequestBody PostDto postDto) {
        return postService.save(postDto);
    }

    @PutMapping("/{id}")
    //This method calls the update method from postService that needs an id and a PostDto object and returns an Optional
    //Then, tries to map the Optional PostDtoGetPostPut by using the .ok() function from ResponseEntity, for this the postDtoGetPostPut has to be present
    //If the optional is empty, executes the orElseGet() implementing a ResponseEntity.notFound().build()
    public ResponseEntity<PostDtoGetPostPut> updatePost(@PathVariable long id, @Valid @RequestBody PostDto postDto) {
        Optional<PostDtoGetPostPut> postDtoGetPostPut = postService.update(id, postDto);
        return postDtoGetPostPut.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    //This method refers to postService.findById() and postService.deleteById() methods. Finds a specific post searching by id and deletes it
    //If the post is found, deletes it.
    //If there is not a post identified by that id, returns 404 Not Found Status
    @DeleteMapping("{id}")
    public ResponseEntity<Post> deletePost(@PathVariable long id) {
        if (postService.deleteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
