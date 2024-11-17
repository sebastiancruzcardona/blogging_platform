package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.dto.*;
import com.eam.blogging_platform.entity.TagsPost;
import com.eam.blogging_platform.service.TagsPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tagsPosts")
public class TagsPostController {

    @Autowired //Singleton backwards for just one TagsPostService instance
    private TagsPostService tagsPostService;


    //This method refers to tagsPostService.findAll() method. Brings out every tags_post stored in database's table tagsPosts as a List of tags_post
    @GetMapping
    public List<Tag_PostDtoGetPostPut> getAllTag_Post() {
        return tagsPostService.findAll();
    }

    @GetMapping("/{id}")
    //This method calls the findById method from tagsPostService that returns an Optional
    //Then, tries to map the Optional tag_postDtoGetPostPut by using the .ok() function from ResponseEntity, for this the account has to be present
    //If the optional is empty, executes the orElseGet() implementing a ResponseEntity.notFound().build()
    //It is equivalent to writing:
        /*if(tag_postDtoGetPostPut.isPresent()){
          return ResponseEntity.ok(tag_postDtoGetPostPut);
        else{
          return ResponseEntity.notFound().build();
        }*/
    public ResponseEntity<Tag_PostDtoGetPostPut> getTag_PostById(@PathVariable long id) {
        Optional<Tag_PostDtoGetPostPut> tag_postDtoGetPostPut = tagsPostService.findById(id);
        return tag_postDtoGetPostPut.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    //This method calls the save method from tagsPostService that needs an Tag_PostDto object and returns an Optional
    //Then, tries to map the Optional tag_postDtoGetPostPut by using the .ok() function from ResponseEntity
    public ResponseEntity<Tag_PostDtoGetPostPut> saveTagPost(@Valid @RequestBody Tag_PostDto tagsPostDto) {
        Optional<Tag_PostDtoGetPostPut> saveTagPost = tagsPostService.save(tagsPostDto);
        return saveTagPost.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    //This method calls the update method from tagsPostService that needs an id and a Tag_PostDto object and returns an Optional
    //Then, tries to map the Optional tag_postDtoGetPostPut by using the .ok() function from ResponseEntity, for this the tag_postDtoGetPostPut has to be present
    //If the optional is empty, executes the orElseGet() implementing a ResponseEntity.notFound().build()
    public ResponseEntity<Tag_PostDtoGetPostPut> updateTagPost(@PathVariable long id, @Valid @RequestBody Tag_PostDto tagPostDto) {
        Optional<Tag_PostDtoGetPostPut> tag_postDtoGetPostPut = tagsPostService.update(id, tagPostDto);
        return tag_postDtoGetPostPut.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //This method refers to tagsPostService.findById() and tagsPostService.deleteById() methods. Finds a specific tagspost searching by id and deletes it
    //If the tag_post is found, deletes it.
    //If there is not a tag_post identified by that id, returns 404 Not Found Status
    @DeleteMapping("/{id}")
    public ResponseEntity<TagsPost> deleteTag_Post(@PathVariable long id) {
        if (tagsPostService.deleteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

