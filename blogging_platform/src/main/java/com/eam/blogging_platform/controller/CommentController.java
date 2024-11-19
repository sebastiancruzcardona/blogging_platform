 package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.dto.*;
import com.eam.blogging_platform.entity.Comment;
import com.eam.blogging_platform.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired //Singleton backwards for just one RoleService instance
    private CommentService commentService;

    //This method refers to commentService.findAll() method. Brings out every comment stored in database table comments as a List of CommentDto
    @GetMapping
    public List<CommentDtoGetPostPut> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    //This method calls the findById method from commentService that returns an Optional
    //Then, tries to map the Optional CommentDtoGetPostPut by using the .ok() function from ResponseEntity, for this the comment has to be present
    //If the optional is empty, executes the orElseGet() implementing a ResponseEntity.notFound().build()
    //It is equivalent to writing:
        /*if(commentDtoGetPostPut.isPresent()){
          return ResponseEntity.ok(commentDtoGetPostPut);
        else{
          return ResponseEntity.notFound().build();
        }*/
    public ResponseEntity<CommentDtoGetPostPut> getCommentById(@PathVariable long id) {
        Optional<CommentDtoGetPostPut> commentDto = commentService.findById(id);
        return commentDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/create")
    public ResponseEntity<CommentDtoGetPostPut> saveComments(@Valid @RequestBody CommentDto commentDto) {
        Optional<CommentDtoGetPostPut> savedComment = commentService.save(commentDto);
        return savedComment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    //This method calls the update method from userService that needs an id and a UserDTO object and returns an Optional
    //Then, tries to map the Optional userDTOGetPostPut by using the .ok() function from ResponseEntity, for this the userDTOGetPostPut has to be present
    //If the optional is empty, executes the orElseGet() implementing a ResponseEntity.notFound().build()
    public ResponseEntity<CommentDtoGetPostPut> updateComment(@PathVariable long id, @Valid @RequestBody CommentUpdateDto commentUpdateDto){
        Optional<CommentDtoGetPostPut> commentDtoGetPostPut = commentService.update(id, commentUpdateDto);
        return commentDtoGetPostPut.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //This method refers to commentService.findById() and commentService.deleteById() methods. Finds a specific comment searching by id and deletes it
    //If the comment is found, deletes it.
    //If there is not a comment identified by that id, returns 404 Not Found Status
    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable long id) {
        if (commentService.deleteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
