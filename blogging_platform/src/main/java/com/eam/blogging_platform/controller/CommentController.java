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

@Controller
public class CommentController {

    @Autowired
    private CommentController commentController;
    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> getAllComments() {
        return commentController.getAllComments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getPostId(@PathVariable long id) {
        Optional<Comment> comment = commentService.findById(id);
        return comment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.save(comment);
    }

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
