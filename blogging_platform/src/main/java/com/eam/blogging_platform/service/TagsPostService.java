package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.TagsPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagsPostService {
    @Autowired
    private TagsPostService tagsPostService;

    public List<TagsPost> findAll() {
        return tagsPostService.findAll();
    }

    public Optional<TagsPost> findById(long id) {
        return tagsPostService.findById(id);
    }

    public TagsPost save(TagsPost tagsPost) {
        return tagsPostService.save(tagsPost);
    }

    public void deleteById(long id) {
        tagsPostService.deleteById(id);
    }
}
