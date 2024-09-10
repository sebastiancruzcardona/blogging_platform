package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagService tagService;

    public List<Tag> findAll() {
        return tagService.findAll();
    }

    public Optional<Tag> findById(long id) {
        return tagService.findById(id);
    }

    public Tag save(Tag tag) {
        return tagService.save(tag);
    }

    public void deleteById(long id){
        tagService.deleteById(id);
    }
}
