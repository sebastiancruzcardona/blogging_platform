package com.eam.blogging_platform.service;

import com.eam.blogging_platform.dto.*;
import com.eam.blogging_platform.entity.*;
import com.eam.blogging_platform.repository.CommentRepository;
import com.eam.blogging_platform.repository.PostRepository;
import com.eam.blogging_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired //Singleton backwards for just one CommentRepository instance
    private CommentRepository commentRepository;

    @Autowired //Singleton backwards for just one userRepository instance
    private UserRepository userRepository;

    @Autowired //Singleton backwards for just one postRepository instance
    private PostRepository postRepository;

    //This method finds all comments stored in database and returns a list of CommentDtoGetPostPut
    //Calls commentRepository.findAll() and uses a for cycle to iterate over the comments and to add to the Arraylist to return
    public List<CommentDtoGetPostPut> findAll() {
        List<CommentDtoGetPostPut> commentToReturn = new ArrayList<>();
        List<Comment> comments = commentRepository.findAll();
        for (Comment comment : comments) {
            CommentDtoGetPostPut commentDtoGetPostPut = new CommentDtoGetPostPut();
            commentDtoGetPostPut.convertToCommentDTO(comment);
            commentToReturn.add(commentDtoGetPostPut);
        }
        return commentToReturn;
    }

    //This method returns an Optional of CommentDtoGetPostPut
    //Using id, if the searched comment exist, returns the optional, if not, returns an empty Optional
    public Optional<CommentDtoGetPostPut> findById(long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            CommentDtoGetPostPut commentDtoGetPostPut = new CommentDtoGetPostPut();
            commentDtoGetPostPut.convertToCommentDTO(comment.get());
            return Optional.of(commentDtoGetPostPut);
        }
        return Optional.empty();
    }

    //This method returns CommentDtoGetPostPut object
    //Creates a comment object, sets its attributes from CommentDtoGetPostPut received as parameter and saves it by calling commentRepository.save()
    //Uses that Comment as an assistant to save calling the repository save() function
    public Optional<CommentDtoGetPostPut> save(CommentDto commentDto) {
        Optional<User> user = userRepository.findById(commentDto.getUserId());
        Optional<Post> post = postRepository.findById(commentDto.getPostId());
        if (user.isPresent() && post.isPresent()) {
            Comment comment = new Comment();
            comment.setComment(commentDto.getComment());
            comment.setUser(user.get());
            comment.setPost(post.get());
            comment.setCreationDate(LocalDateTime.now());
            comment.setLastUpdateDate(LocalDateTime.now());
            CommentDtoGetPostPut savedComment = new CommentDtoGetPostPut();
            savedComment.convertToCommentDTO(commentRepository.save(comment));
            return Optional.of(savedComment);
        } else {
            return Optional.empty();
        }
    }

    //This method returns an Optional that can be present or empty.
    //First, it tries to find the Comment by id, then, if the Optional comment is present, sets the attributes and returns an Optional
    //If there is not a comment identified by that id, returns an empty optional
    public Optional<CommentDtoGetPostPut> update(long id, CommentDto commentDto) {
        Optional<Comment> comment = commentRepository.findById(id);
        Optional<User> user = userRepository.findById(commentDto.getUserId());
        Optional<Post> post = postRepository.findById(commentDto.getPostId());
        if (comment.isPresent() && user.isPresent() && post.isPresent()) {
            Comment commentUpdate = comment.get();
            commentUpdate.setComment(comment.get().getComment());
            commentUpdate.setUser(user.get()); // Replace with actual Post retrieval logic
            commentUpdate.setPost(post.get()); // Replace with actual Category retrieval logic
            commentUpdate.setCreationDate(comment.get().getCreationDate());
            commentUpdate.setLastUpdateDate(comment.get().getLastUpdateDate());
            CommentDtoGetPostPut commentDtoGetPostPut = new CommentDtoGetPostPut();
            commentDtoGetPostPut.convertToCommentDTO(commentUpdate);
            return Optional.of(commentDtoGetPostPut);
        } else {
            return Optional.empty();
        }
    }

    //This method, validating the Optional in the if block, returns true if deletion was made or false if not
    public boolean deleteById(long id) {
        if (commentRepository.findById(id).isPresent()) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
