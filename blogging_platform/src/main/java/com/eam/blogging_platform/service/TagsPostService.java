package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.TagsPost;
import com.eam.blogging_platform.repository.TagsPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagsPostService {
    @Autowired //Singleton backwards for just one TagsPostRepository instance
    private TagsPostRepository tagsPostRepository;

    //This method brings out every TagsPost stored in table tagsPost in database
    public List<TagsPost> findAll() {
        return tagsPostRepository.findAll();
    }

    //This method finds a specific tagsPost searching by id
    public Optional<TagsPost> findById(long id) {
        return tagsPostRepository.findById(id);
    }

    //This method saves a new tagsPost in database table tagsPost

    public TagsPost save(TagsPost tagsPost) {
        return tagsPostRepository.save(tagsPost);
    }

    //This method deletes a specific tagsPost by using its id

    public void deleteById(long id) {
        tagsPostRepository.deleteById(id);
    }
}
