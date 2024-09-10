package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.Comment;
import com.eam.blogging_platform.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired //Singleton backwards for just one CommentRepository instance
    private CommentRepository commentRepository;

    //This method brings out every Comment stored in table comment in database

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    //This method finds a specific comment searching by id
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    //This method saves a new comment in database table comment
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    //This method deletes a specific comment by using its id

    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
