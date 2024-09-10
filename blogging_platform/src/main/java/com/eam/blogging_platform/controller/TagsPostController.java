package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.entity.TagsPost;
import com.eam.blogging_platform.service.TagsPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tagsPost")
public class TagsPostController {

    @Autowired //Singleton backwards for just one tagsPostService instance
    private TagsPostService tagsPostService;

    //This method refers to tagsPostService.findAll() method. Brings out every TagsPost stored in database table tagsPost as a List of tagsPost
    @GetMapping
    public List<TagsPost> getAllTagsPosts() {
        return tagsPostService.findAll();
    }

    //This method refers to tagsPostService.findById() method. Finds a specific role searching by id
    //If the tagsPost is found, maps the ResponseEntity and returns a 200 OK Status.
    //If there is not a tagsPost identified by that id, returns 404 Not Found Status
    @GetMapping("/{id}")
    public ResponseEntity<TagsPost> getPostById(@PathVariable long id) {
        Optional<TagsPost> tagsPost = tagsPostService.findById(id);
        return tagsPost.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //This method refers to tagsPostService.save() method. Saves a new tagsPost in database table tagsPost
    @PostMapping
    public TagsPost createPost(@RequestBody TagsPost tagsPost) {
        return tagsPostService.save(tagsPost);
    }

    //This method refers to tagsPostService.findById() and roleService.save() methods. Finds a specific tagsPost searching by id and updates it
    //If the tagsPost is found, sets the attributes to the tagsPost in edition, saves to update and returns a 200 OK Status.
    //If there is not a tagsPost identified by that id, returns 404 Not Found Status
    @PutMapping("/{id}")
    public ResponseEntity<TagsPost> updatePost(@PathVariable long id, @RequestBody TagsPost content) {
        Optional<TagsPost> tagsPost = tagsPostService.findById(id);
        if (tagsPost.isPresent()) {
            TagsPost updatedTagsPost = tagsPost.get();
            return ResponseEntity.ok(tagsPostService.save(updatedTagsPost));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //This method refers to tagsPostService.findById() and tagsPostService.deleteById() methods. Finds a specific tagsPost searching by id and deletes it
    //If the tagsPost is found, deletes it.
    //If there is not a tagsPost identified by that id, returns 404 Not Found Status
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTagsPost(@PathVariable long id) {
        if (tagsPostService.findById(id).isPresent()) {
            tagsPostService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}

