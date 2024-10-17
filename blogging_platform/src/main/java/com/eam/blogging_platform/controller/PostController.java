package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.dto.PostDto;
import com.eam.blogging_platform.dto.PostDtoGetPostPut;
import com.eam.blogging_platform.dto.PostLikesDislikesDTO;
import com.eam.blogging_platform.dto.PostUpdateDTO;
import com.eam.blogging_platform.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * Retrieves all posts from the database.
     * @return List of PostDTOGetPostPut representing all posts.
     */
    @GetMapping
    public List<PostDtoGetPostPut> getAllPosts() {
        return postService.findAll();
    }

    /**
     * Retrieves a specific post by its ID.
     * @param id The ID of the post to retrieve.
     * @return ResponseEntity containing the PostDTOGetPostPut if found, otherwise 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDtoGetPostPut> getPostById(@PathVariable long id) {
        Optional<PostDtoGetPostPut> postDTO = postService.findById(id);
        return postDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new post.
     * @param postDTO The post data to create.
     * @return ResponseEntity containing the created PostDTOGetPostPut if successful, otherwise 400 Bad Request.
     */
    @PostMapping("/save")
    public ResponseEntity<PostDtoGetPostPut> createPost(@Valid @RequestBody PostDto postDTO) {
        Optional<PostDtoGetPostPut> savedPost = postService.save(postDTO);
        return savedPost.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Updates an existing post by its ID.
     * @param id The ID of the post to update.
     * @param postUpdateDTO The updated post data.
     * @return ResponseEntity containing the updated PostDTOGetPostPut if successful, otherwise 404 Not Found.
     */
    @PutMapping("/{id}")
    //This method calls the update method from postService that needs an id and a PostDto object and returns an Optional
    //Then, tries to map the Optional PostDtoGetPostPut by using the .ok() function from ResponseEntity, for this the postDtoGetPostPut has to be present
    //If the optional is empty, executes the orElseGet() implementing a ResponseEntity.notFound().build()
    public ResponseEntity<PostDtoGetPostPut> updatePost(@PathVariable long id, @Valid @RequestBody PostUpdateDTO postUpdateDTO) {
        Optional<PostDtoGetPostPut> postDtoGetPostPut = postService.update(id, postUpdateDTO);
        return postDtoGetPostPut.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}/likesDislikes")
    //This method calls the update method from postService that needs an id and a PostDto object and returns an Optional
    //Then, tries to map the Optional PostDtoGetPostPut by using the .ok() function from ResponseEntity, for this the postDtoGetPostPut has to be present
    //If the optional is empty, executes the orElseGet() implementing a ResponseEntity.notFound().build()
    public ResponseEntity<PostDtoGetPostPut> updatePostLikesDislikes(@PathVariable long id, @Valid @RequestBody PostLikesDislikesDTO postLikesDislikesDTO) {
        Optional<PostDtoGetPostPut> postDtoGetPostPut = postService.updateLikesDislikes(id, postLikesDislikesDTO);
        return postDtoGetPostPut.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes a specific post by its ID.
     * @param id The ID of the post to delete.
     * @return ResponseEntity with status 200 OK if deleted, otherwise 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        if (postService.deleteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
