package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired  //Singleton backwards for just one PostRepository instance
    private PostService postService;

    //This method brings out every post stored in table post in database
    public List<Post> findAll() {
        return postService.findAll();
    }
    //This method finds a specific post searching by id

    public Optional<Post> findById(long id) {
        return postService.findById(id);
    }

    //This method saves a new post in database table post
    public Post save(Post post) {
        return postService.save(post);
    }

    //This method deletes a specific post by using its id

    public void deleteById(long id) {
        postService.deleteById(id);
    }
}
