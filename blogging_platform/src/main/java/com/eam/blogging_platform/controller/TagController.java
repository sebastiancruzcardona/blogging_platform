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

@RestController
@RequestMapping("/api/roles")
public class TagController {

    @Autowired //Singleton backwards for just one TagService instance
    private TagService tagService;

    //This method refers to tagService.findAll() method. Brings out every Tag stored in database table tags as a List of tags
    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.findAll();
    }

    //This method refers to tagService.findById() method. Finds a specific tag searching by id
    //If the tag is found, maps the ResponseEntity and returns a 200 OK Status.
    //If there is not a tag identified by that id, returns 404 Not Found Status
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getPostById(@PathVariable long id) {
        Optional<Tag> tag = tagService.findById(id);
        return tag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //This method refers to tagService.save() method. Saves a new tag in database table tag
    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.save(tag);
    }

    //This method refers to tagService.findById() and tagService.save() methods. Finds a specific role searching by id and updates it
    //If the tag is found, sets the attributes to the tag in edition, saves to update and returns a 200 OK Status.
    //If there is not a tag identified by that id, returns 404 Not Found Status
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

    //This method refers to tagService.findById() and tagService.deleteById() methods. Finds a specific tag searching by id and deletes it
    //If the tag is found, deletes it.
    //If there is not a tag identified by that id, returns 404 Not Found Status
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
