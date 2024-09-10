package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostService postService;

    public List<Post> findAll() {
        return postService.findAll();
    }

    public Optional<Post> findById(long id) {
        return postService.findById(id);
    }

    public Post save(Post post) {
        return postService.save(post);
    }

    public void deleteById(long id) {
        postService.deleteById(id);
    }
}
