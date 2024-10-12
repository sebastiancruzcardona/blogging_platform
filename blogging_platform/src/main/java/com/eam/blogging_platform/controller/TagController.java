package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.dto.TagDto;
import com.eam.blogging_platform.dto.TagDtoGetPostPut;
import com.eam.blogging_platform.entity.Tag;
import com.eam.blogging_platform.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired //Singleton backwards for just one tagService instance
    private TagService tagService;

    //This method refers to tagService.findAll() method. Brings out every role stored in database table role as a List of RoleDTOs
    @GetMapping
    public List<TagDtoGetPostPut> getAllTags() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    //This method calls the findById method from tagService that returns an Optional
    //Then, tries to map the Optional TagDtoGetPostPut by using the .ok() function from ResponseEntity, for this the tag has to be present
    //If the optional is empty, executes the orElseGet() implementing a ResponseEntity.notFound().build()
    //It is equivalent to writing:
        /*if(tagDtoGetPostPut.isPresent()){
          return ResponseEntity.ok(tagDtoGetPostPut);
        else{
          return ResponseEntity.notFound().build();
        }*/
    public ResponseEntity<TagDtoGetPostPut> getTagById(@PathVariable long id){
        Optional<TagDtoGetPostPut> tagDtoGetPostPut = tagService.findById(id);
        return tagDtoGetPostPut.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //This method refers to tagService.save() method. Saves a new tag in database table tags
    @PostMapping
    public TagDtoGetPostPut createRole(@Valid @RequestBody TagDto tagDto){
        return tagService.save(tagDto);
    }

    @PutMapping("/{id}")
    //This method calls the update method from tagService that needs an id and a TagDto object and returns an Optional
    //Then, tries to map the Optional TagDtoGetPostPut by using the .ok() function from ResponseEntity, for this the tagDtoGetPostPut has to be present
    //If the optional is empty, executes the orElseGet() implementing a ResponseEntity.notFound().build()
    public ResponseEntity<TagDtoGetPostPut> updateTag(@PathVariable long id, @Valid @RequestBody TagDto tagDto){
        Optional<TagDtoGetPostPut> tagDtoGetPostPut = tagService.update(id, tagDto);
        return tagDtoGetPostPut.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    //This method refers to tag.findById() and tagService.deleteById() methods. Finds a specific tag searching by id and deletes it
    //If the role is found, deletes it.
    //If there is not a tag identified by that id, returns 404 Not Found Status
    @DeleteMapping("{id}")
    public ResponseEntity<Tag> deleteTag(@PathVariable long id){
        if(tagService.deleteById(id)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
