package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.Post;
import com.eam.blogging_platform.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired  //Singleton backwards for just one PostRepository instance
    private PostRepository postRepository;

    //This method brings out every post stored in table post in database
    public List<Post> findAll() {
        return postRepository.findAll();
    }
    //This method finds a specific post searching by id

    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }

    //This method saves a new post in database table post
    public Post save(Post post) {
        return postRepository.save(post);
    }

    //This method deletes a specific post by using its id

    public void deleteById(long id) {
        postRepository.deleteById(id);
    }
}
