package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.Comment;
import com.eam.blogging_platform.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Optional<Comment> findById(long id){
        return commentRepository.findById(id);
    }

    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }

    public void deleteById(long id){
        commentRepository.deleteById(id);
    }
}
