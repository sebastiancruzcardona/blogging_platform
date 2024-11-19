package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.dto.*;
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

    //Method to get all posts that a specific user has
    @GetMapping("/user/{id}")
    public List<PostDtoGetPostPut> getPostsByUserId(@PathVariable long id){
        return postService.findPostsByUserId(id);
    }

    //This is a search filter method. Gets all published posts that belong to a specific author by id
    @GetMapping("/author/{id}")
    public List<PostDtoGetPostPut> getPostsByAuthorId(@PathVariable long id){
        return postService.findPostsByAuthorId(id);
    }

    //This is a search filter method. Gets all published posts that belong to a specific author by username
    @GetMapping("/author/username/{username}")
    public ResponseEntity<List<PostDtoGetPostPut>> getPostsByAuthorUsername(@PathVariable String username){
        Optional<List<PostDtoGetPostPut>> posts = postService.findPostsByAuthorUsername(username);
        return posts.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    //This is a search filter method. Gets all published posts that belong to a specific category
    @GetMapping("/category/{id}")
    public List<PostDtoGetPostPut> getPostsByCategoryId(@PathVariable long id){
        return postService.findPostsByCategoryId(id);
    }

    //This is a search filter method. Gets all published posts that belong to a specific category by category name
    @GetMapping("/category/name/{categoryName}")
    public ResponseEntity<List<PostDtoGetPostPut>> getPostsByCategoryName(@PathVariable String categoryName){
        Optional<List<PostDtoGetPostPut>> posts = postService.findPostsByCategoryName(categoryName);
        return posts.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    //This is a search filter method. Gets all published posts that have a specific tag
    @GetMapping("/tag/{id}")
    public List<PostDtoGetPostPut> findPostsByTagId(@PathVariable long id){
        return postService.findPostsByTagId(id);
    }

    //This is a search filter method. Gets all published posts that belong to a specific tag by tag name
    @GetMapping("/tag/name/{tagName}")
    public ResponseEntity<List<PostDtoGetPostPut>> getPostsByTagName(@PathVariable String tagName){
        Optional<List<PostDtoGetPostPut>> posts = postService.findPostsByTagName(tagName);
        return posts.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    //This is a search filter method. Serch a specific post by title
    @GetMapping("/title/{title}")
    public ResponseEntity<PostDtoGetPostPut> getPostsByTitle(@PathVariable String title){
        Optional<PostDtoGetPostPut> post = postService.findByTitle(title);
        return post.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    //This method returns all published (status = 2) posts
    @GetMapping("/published")
    public List<PostDtoGetPostPut> getPublishedPosts() {
        return postService.findPublished();
    }

    //This is a search filter method. Search posts by content text coincidences
    @GetMapping("/content")
    public List<PostDtoGetPostPut> getPostsByContent(@Valid @RequestBody PostFindByContentDto postFindByContentDto) {
        return postService.findByContent(postFindByContentDto);
    }

    //This is a search filter method. Gets all publised posts and sorts them descending by number of likes
    @GetMapping("/popularityOrdered")
    public List<PostDtoGetPostPut> getPostsPopularityOrdered() {
        return postService.findAllPopularityOrdered();
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
