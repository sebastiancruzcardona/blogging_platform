package com.eam.blogging_platform.service;

import com.eam.blogging_platform.dto.*;
import com.eam.blogging_platform.entity.*;
import com.eam.blogging_platform.repository.PostRepository;
import com.eam.blogging_platform.repository.TagRepository;
import com.eam.blogging_platform.repository.TagsPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagsPostService {
    @Autowired //Singleton backwards for just one TagsPostRepository instance
    private TagsPostRepository tagsPostRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;

    //This method finds all users stored in database and returns a list of Tag_PostDtoGetPostPut
    //Calls tagsPostRepository.findAll() and uses a for cycle to iterate over the users and to add to the Arraylist to return
    public List<Tag_PostDtoGetPostPut> findAll(){
        List<Tag_PostDtoGetPostPut> tag_postToReturn = new ArrayList<>();
        List<TagsPost> tagsPost = tagsPostRepository.findAll();
        for (TagsPost tag_post : tagsPost) {
            Tag_PostDtoGetPostPut tagDtoGetPostPut = new Tag_PostDtoGetPostPut();
            tagDtoGetPostPut.convertToTagPostDTO(tag_post);
            tag_postToReturn.add(tagDtoGetPostPut);
        }
        return tag_postToReturn;
    }

    //This method returns an Optional of Tag_PostDtoGetPostPut
    //Using id, if the searched user exist, returns the optional, if not, returns an empty Optional
    public Optional<Tag_PostDtoGetPostPut> findById(long id){
        Optional<TagsPost> tag_post = tagsPostRepository.findById(id);
        if(tag_post.isPresent()){
            Tag_PostDtoGetPostPut tag_postDtoGetPostPut = new Tag_PostDtoGetPostPut();
            tag_postDtoGetPostPut.convertToTagPostDTO(tag_post.get());
            return Optional.of(tag_postDtoGetPostPut);
        }
        return Optional.empty();
    }

    //This method returns the list tagsPost of a post
    //Calls tagsPostRepository.findByPostId() and uses a for cycle to iterate over the tagsPosts and to add to the Arraylist to return
    public List<Tag_PostDtoGetPostPut> findTagsPostByPostId(long id){
        List<Tag_PostDtoGetPostPut> tagsPostToReturn = new ArrayList<>();
        List<TagsPost> tagsPosts = tagsPostRepository.findByPostId(id);
        for (TagsPost tagsPost : tagsPosts) {
            Tag_PostDtoGetPostPut tag_PostDtoGetPostPut = new Tag_PostDtoGetPostPut();
            tag_PostDtoGetPostPut.convertToTagPostDTO(tagsPost);
            tagsPostToReturn.add(tag_PostDtoGetPostPut);
        }
        return tagsPostToReturn;
    }

    //This method returns an Optional of Tag_PostDtoGetPostPut
    //First validates if the associated tag_post exist
    //Creates a User object, sets its attributes from Tag_PostDto received as parameter and saves it by calling tagsPostRepository.save()
    //Uses that TagsPost as an assistant to save calling the repository save() function
    //If the associated tag_post does not exist returns an empty Optional
    public Optional<Tag_PostDtoGetPostPut> save(Tag_PostDto tag_postDto){
        if(tagsPostRepository.findByPostIdAndTagId(tag_postDto.getPostId(), tag_postDto.getTagId()).isPresent()){
            return Optional.empty(); //Return an empty Optional if the tagsPost already exist
        }
        Optional<Post> post = postRepository.findById(tag_postDto.getPostId());
        Optional<Tag> tag = tagRepository.findById(tag_postDto.getTagId());
        if(post.isPresent() && tag.isPresent()){
            TagsPost tag_post = new TagsPost();
            tag_post.setPost(post.get());
            tag_post.setTag(tag.get());
            Tag_PostDtoGetPostPut savedTag_Post = new Tag_PostDtoGetPostPut();
            savedTag_Post.convertToTagPostDTO(tagsPostRepository.save(tag_post));
            return Optional.of(savedTag_Post);
        }else{
            return Optional.empty();
        }
    }

    //This method returns an Optional of Tag_PostDtoGetPostPut that can be present or empty.
    //First, it tries to find the tag_post by id and the tag, post by id, then, if the Optionals tag_post , tag and post are present, sets the attributes and returns an Optional
    //If there is not a tag_post identified by that id, and/or the tag, post does not exist, returns an empty optional
    public Optional<Tag_PostDtoGetPostPut> update(long id, Tag_PostDto tag_PostDto){
        Optional<TagsPost> tag_post = tagsPostRepository.findById(id);
        Optional<Post> post = postRepository.findById(tag_PostDto.getPostId());
        Optional<Tag> tag = tagRepository.findById(tag_PostDto.getTagId());
        if(tag_post.isPresent() && post.isPresent() && tag.isPresent()){
            TagsPost tag_postUpdate = tag_post.get();
            tag_postUpdate.setPost(post.get());
            tag_postUpdate.setTag(tag.get());
            Tag_PostDtoGetPostPut tag_postDtoGetPostPut = new Tag_PostDtoGetPostPut();
            tag_postDtoGetPostPut.convertToTagPostDTO(tag_postUpdate);
            return Optional.of(tag_postDtoGetPostPut);
        }
        return Optional.empty();
    }

    //This method, validating the Optional in the if block, returns true if deletion was made or false if not
    public boolean deleteById(long id){
        if(tagsPostRepository.findById(id).isPresent()){
            tagsPostRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
