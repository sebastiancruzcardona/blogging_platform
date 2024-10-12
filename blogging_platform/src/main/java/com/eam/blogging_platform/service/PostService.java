package com.eam.blogging_platform.service;

import com.eam.blogging_platform.dto.PostDto;
import com.eam.blogging_platform.dto.PostDtoGetPostPut;
import com.eam.blogging_platform.entity.Post;
import com.eam.blogging_platform.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired  //Singleton backwards for just one PostRepository instance
    private PostRepository postRepository;

    //This method finds all posts stored in database and returns a list of PostDtoGetPostPut
    //Calls postRepository.findAll() and uses a for cycle to iterate over the posts and to add to the Arraylist to return
    public List<PostDtoGetPostPut> findAll() {
        List<PostDtoGetPostPut> postToReturn = new ArrayList<>();
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            PostDtoGetPostPut postDtoGetPostPut = new PostDtoGetPostPut();
            postDtoGetPostPut.convertToPostDTO(post);
            postToReturn.add(postDtoGetPostPut);
        }
        return postToReturn;
    }

    //This method returns an Optional of PostDtoGetPostPut
    //Using id, if the searched post exist, returns the optional, if not, returns an empty Optional
    public Optional<PostDtoGetPostPut> findById(long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            PostDtoGetPostPut postDtoGetPostPut = new PostDtoGetPostPut();
            postDtoGetPostPut.convertToPostDTO(post.get());
            return Optional.of(postDtoGetPostPut);
        }
        return Optional.empty();
    }

    //This method returns PostDtoGetPostPut object
    //Creates a post object, sets its attributes from PostDtoGetPostPut received as parameter and saves it by calling postRepository.save()
    //Uses that post as an assistant to save calling the repository save() function
    public PostDtoGetPostPut save(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreation_date(LocalDateTime.now());
        post.setLastUpdateDate(LocalDateTime.now());
        PostDtoGetPostPut postDtoGetPostPut = new PostDtoGetPostPut();
        postDtoGetPostPut.convertToPostDTO(postRepository.save(post));
        return postDtoGetPostPut;
    }

    //This method returns an Optional that can be present or empty.
    //First, it tries to find the post by id, then, if the Optional post is present, sets the attributes and returns an Optional
    //If there is not a post identified by that id, returns an empty optional
    public Optional<PostDtoGetPostPut> update(long id, PostDto postDto) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post updatedPosts = post.get();
            updatedPosts.setTitle(postDto.getTitle());
            PostDtoGetPostPut postDtoGetPostPut = new PostDtoGetPostPut();
            postDtoGetPostPut.convertToPostDTO(postRepository.save(updatedPosts));
            return Optional.of(postDtoGetPostPut);
        } else {
            return Optional.empty();
        }
    }

    //This method, validating the Optional in the if block, returns true if deletion was made or false if not
    public boolean deleteById(long id) {
        if (postRepository.findById(id).isPresent()) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
