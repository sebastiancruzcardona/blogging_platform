package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.entity.Tag;
import com.eam.blogging_platform.entity.Tag;
import com.eam.blogging_platform.service.TagService;
import com.eam.blogging_platform.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class TagController {

    @Autowired
    private TagController tagController;
    @Autowired
    private TagService tagService;

    @GetMapping
    public List<Tag> getAllTags() {
        return tagController.getAllTags();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getPostId(@PathVariable long id) {
        Optional<Tag> tag = tagService.findById(id);
        return tag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.save(tag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable long id, @RequestBody Tag creationDate) {
        Optional<Tag> tag = tagService.findById(id);
        if (tag.isPresent()) {
            Tag updatedTag = tag.get();
            updatedTag.setCreationDate(creationDate.getCreationDate());

            return ResponseEntity.ok(tagService.save(updatedTag));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        if (tagService.findById(id).isPresent()) {
            tagService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
