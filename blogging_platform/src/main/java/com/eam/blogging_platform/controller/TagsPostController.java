package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.entity.TagsPost;
import com.eam.blogging_platform.service.TagsPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class TagsPostController {

    @Autowired
    private TagsPostController tagsPostController;
    @Autowired
    private TagsPostService tagsPostService;

    @GetMapping
    public List<TagsPost> getAllTagsPosts() {
        return tagsPostController.getAllTagsPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagsPost> getPostId(@PathVariable long id) {
        Optional<TagsPost> tagsPost = tagsPostService.findById(id);
        return tagsPost.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TagsPost createPost(@RequestBody TagsPost tagsPost) {
        return tagsPostService.save(tagsPost);
    }

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

