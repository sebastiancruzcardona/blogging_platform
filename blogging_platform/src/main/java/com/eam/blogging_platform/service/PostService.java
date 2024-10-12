package com.eam.blogging_platform.service;

import com.eam.blogging_platform.dto.PostDto;
import com.eam.blogging_platform.dto.PostDtoGetPostPut;
import com.eam.blogging_platform.entity.Post;
import com.eam.blogging_platform.entity.Status;
import com.eam.blogging_platform.entity.User;
import com.eam.blogging_platform.repository.PostRepository;
import com.eam.blogging_platform.repository.StatusRepository;
import com.eam.blogging_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    // This method finds all posts stored in the database and returns a list of PostDTOGetPostPut
    public List<PostDtoGetPostPut> findAll() {
        List<PostDtoGetPostPut> postsToReturn = new ArrayList<>();
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            PostDtoGetPostPut postDTOGetPostPut = new PostDtoGetPostPut();
            postDTOGetPostPut.convertToPostDTO(post);
            postsToReturn.add(postDTOGetPostPut);
        }
        return postsToReturn;
    }

    // This method returns an Optional of PostDTOGetPostPut
    // Using id, if the searched post exists, returns the optional, if not, returns an empty Optional
    public Optional<PostDtoGetPostPut> findById(long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            PostDtoGetPostPut postDTOGetPostPut = new PostDtoGetPostPut();
            postDTOGetPostPut.convertToPostDTO(post.get());
            return Optional.of(postDTOGetPostPut);
        }
        return Optional.empty();
    }

    // This method returns an Optional of PostDTOGetPostPut
    // First validates if the associated user and status exist
    // Creates a Post object, sets its attributes from PostDTO received as parameter and saves it by calling postRepository.save()
    // Uses that Post as an assistant to save calling the repository save() function
    // If the associated user or status does not exist, returns an empty Optional
    public Optional<PostDtoGetPostPut> save(PostDto postDTO) {
        Optional<User> user = userRepository.findById(postDTO.getUserId());
        Optional<Status> status = statusRepository.findById(postDTO.getStatusId());
        if (user.isPresent() && status.isPresent()) {
            Post post = new Post();
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());
            post.setUser(user.get());
            post.setStatus(status.get());
            post.setLikes(0);
            post.setDislikes(0);
            post.setCreation_date(LocalDateTime.now());
            post.setLastUpdateDate(LocalDateTime.now());
            //When we have the statuses parameters in db, we can set the publication date here
            //If status is published, set publication date
            PostDtoGetPostPut savedPost = new PostDtoGetPostPut();
            savedPost.convertToPostDTO(postRepository.save(post));
            return Optional.of(savedPost);
        } else {
            return Optional.empty();
        }
    }

    // This method returns an Optional of PostDTOGetPostPut that can be present or empty.
    // First, it tries to find the post by id and the user and status by id, then, if the Optionals post, user, and status are present, sets the attributes and returns an Optional
    // If there is not a post identified by that id, and/or the user or status does not exist, returns an empty optional
    public Optional<PostDtoGetPostPut> update(long id, PostDto postDTO) {
        Optional<Post> post = postRepository.findById(id);
        Optional<User> user = userRepository.findById(postDTO.getUserId());
        Optional<Status> status = statusRepository.findById(postDTO.getStatusId());
        if (post.isPresent() && user.isPresent() && status.isPresent()) {
            Post postUpdate = post.get();
            postUpdate.setTitle(postDTO.getTitle());
            postUpdate.setContent(postDTO.getContent());
            postUpdate.setUser(user.get());
            postUpdate.setStatus(status.get());
            //postUpdate.setLikes(postDTO.getLikes());
            //postUpdate.setDislikes(postDTO.getDislikes());
            postUpdate.setLastUpdateDate(LocalDateTime.now());
            PostDtoGetPostPut postDTOGetPostPut = new PostDtoGetPostPut();
            postDTOGetPostPut.convertToPostDTO(postRepository.save(postUpdate));
            return Optional.of(postDTOGetPostPut);
        }
        return Optional.empty();
    }

    // This method, validating the Optional in the if block, returns true if deletion was made or false if not
    public boolean deleteById(long id) {
        if (postRepository.findById(id).isPresent()) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
