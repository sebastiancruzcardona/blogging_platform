package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.Tag;
import com.eam.blogging_platform.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired //Singleton backwards for just one TagRepository instance
    private TagRepository tagRepository;

    //This method brings out every Tag stored in table tag in database
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    //This method finds a specific tag searching by id
    public Optional<Tag> findById(long id) {
        return tagRepository.findById(id);
    }

    //This method saves a new tag in database table tag
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    //This method deletes a specific tag by using its id

    public void deleteById(long id){
        tagRepository.deleteById(id);
    }
}
