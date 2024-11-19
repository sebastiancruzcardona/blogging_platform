package com.eam.blogging_platform.service;

import com.eam.blogging_platform.dto.*;
import com.eam.blogging_platform.entity.Role;
import com.eam.blogging_platform.entity.Tag;
import com.eam.blogging_platform.entity.User;
import com.eam.blogging_platform.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired //Singleton backwards for just one TagRepository instance
    private TagRepository tagRepository;

    //This method brings out every Tag stored in table tag in database
    public List<TagDtoGetPostPut> findAll() {
        List<TagDtoGetPostPut> tagsToReturn = new ArrayList<>();
        List<Tag> tags = tagRepository.findAll();
        for (Tag tag : tags) {
            TagDtoGetPostPut tagDTOGetPostPut = new TagDtoGetPostPut();
            tagDTOGetPostPut.convertToTagDTO(tag);
            tagsToReturn.add(tagDTOGetPostPut);
        }
        return tagsToReturn;
    }

    //This method returns an Optional of TagDtoGetPostPut
//Using id, if the searched tag exist, returns the optional, if not, returns an empty Optional
    public Optional<TagDtoGetPostPut> findById(long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isPresent()) {
            TagDtoGetPostPut tagDtoGetPutPost = new TagDtoGetPostPut();
            tagDtoGetPutPost.convertToTagDTO(tag.get());
            return Optional.of(tagDtoGetPutPost);
        }
        return Optional.empty();
    }

    //This method returns an Optional of TagDtoGetPostPut object or an empty Optional
    //Validates if there is a tag with the same name
    //If not, creates a tag object, sets its attributes from TagDTOGetPostPut received as parameter and saves it by calling tagRepository.save()
    //Uses that Tag as an assistant to save calling the repository save() function
    public Optional<TagDtoGetPostPut> save(TagDto tagDto) {
        if(tagRepository.findByTag(tagDto.getTag()).isPresent()){
            return Optional.empty();
        }
        Tag tag = new Tag();
        tag.setTag(tagDto.getTag());
        tag.setCreationDate(LocalDateTime.now());
        TagDtoGetPostPut savedTag = new TagDtoGetPostPut();
        savedTag.convertToTagDTO(tagRepository.save(tag));
        return Optional.of(savedTag);
    }
    

    //This method returns an Optional that can be present or empty.
//First, it tries to find the Tag by id, then, if the Optional tag is present, sets the attributes and returns an Optional
//If there is not a tag identified by that id, returns an empty optional
    public Optional<TagDtoGetPostPut> update(long id, TagDto tagDto) {
        if(tagRepository.findByTag(tagDto.getTag()).isPresent()){
            return Optional.empty();
        }
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isPresent()) {
            Tag updatedTag = tag.get();
            updatedTag.setTag(tagDto.getTag());
            TagDtoGetPostPut tagDtoGetPostPut = new TagDtoGetPostPut();
            tagDtoGetPostPut.convertToTagDTO(tagRepository.save(updatedTag));
            return Optional.of(tagDtoGetPostPut);
        } else {
            return Optional.empty();
        }
    }

    //This method, validating the Optional in the if block, returns true if deletion was made or false if not
    public boolean deleteById(long id) {
        if (tagRepository.findById(id).isPresent()) {
            tagRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
