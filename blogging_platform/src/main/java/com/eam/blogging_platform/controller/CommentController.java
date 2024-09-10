package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.entity.Comment;
import com.eam.blogging_platform.entity.Post;
import com.eam.blogging_platform.service.CommentService;
import com.eam.blogging_platform.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired //Singleton backwards for just one commentService instance
    private CommentService commentService;

    //This method refers to commentService.findAll() method. Brings out every Comment stored in database table comment as a List of comments
    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.findAll();
    }

    //This method refers to commentService.findById() method. Finds a specific comment searching by id
    //If the comment is found, maps the ResponseEntity and returns a 200 OK Status.
    //If there is not a comment identified by that id, returns 404 Not Found Status
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getPostById(@PathVariable long id) {
        Optional<Comment> comment = commentService.findById(id);
        return comment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //This method refers to commentService.save() method. Saves a new comment in database table comment
    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.save(comment);
    }

    //This method refers to commentService.findById() and commentService.save() methods. Finds a specific comment searching by id and updates it
    //If the comment is found, sets the attributes to the comment in edition, saves to update and returns a 200 OK Status.
    //If there is not a comment identified by that id, returns 404 Not Found Status
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable long id, @RequestBody Comment creationDate) {
        Optional<Comment> comment = commentService.findById(id);
        if (comment.isPresent()) {
            Comment updatedComment = comment.get();
            updatedComment.setLastUpdateDate(creationDate.getCreationDate());

            return ResponseEntity.ok(commentService.save(updatedComment));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //This method refers to commentService.findById() and commentService.deleteById() methods. Finds a specific comment searching by id and deletes it
    //If the comment is found, deletes it.
    //If there is not a comment identified by that id, returns 404 Not Found Status
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        if (commentService.findById(id).isPresent()) {
            commentService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
