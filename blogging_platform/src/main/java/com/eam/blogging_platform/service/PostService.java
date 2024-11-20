package com.eam.blogging_platform.service;

import com.eam.blogging_platform.dto.*;
import com.eam.blogging_platform.entity.*;
import com.eam.blogging_platform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    // This method finds all posts stored in the database and returns a list of PostDTOGetPostPut
    public List<PostDtoGetPostPut> findAll() {
        List<PostDtoGetPostPut> postsToReturn = new ArrayList<>();
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            PostDtoGetPostPut postDTOGetPostPut = new PostDtoGetPostPut();
            postDTOGetPostPut.convertToPostDTO(post);
            postsToReturn.add(postDTOGetPostPut);
        }
        return postsToReturn;
    }

    // This method returns an Optional of PostDTOGetPostPut
    // Using id, if the searched post exists, returns the optional, if not, returns an empty Optional
    public Optional<PostDtoGetPostPut> findById(long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            PostDtoGetPostPut postDTOGetPostPut = new PostDtoGetPostPut();
            postDTOGetPostPut.convertToPostDTO(post.get());
            return Optional.of(postDTOGetPostPut);
        }
        return Optional.empty();
    }

    //This method returns a list of Optionals of PostDtoGetPostPut. This returns the list of posts of a user
    //Calls postRepository.findPostByUserId() and uses a for cycle to iterate over the posts and to add to the Arraylist to return
    public List<PostDtoGetPostPut> findPostsByUserId(long id){
        List<PostDtoGetPostPut> postsToReturn = new ArrayList<>();
        List<Post> posts = postRepository.findPostByUserId(id);
        for (Post post : posts) {
            PostDtoGetPostPut postDtoGetPostPut = new PostDtoGetPostPut();
            postDtoGetPostPut.convertToPostDTO(post);
            postsToReturn.add(postDtoGetPostPut);
        }
        return postsToReturn;
    }

    //This method returns a list of Optionals of PostDtoGetPostPut. This returns the list of published posts of an author
    //Calls postRepository.findPostByUserId() and uses a for cycle to iterate over the posts and to add to the Arraylist to return
    public List<PostDtoGetPostPut> findPostsByAuthorId(long id){
        List<PostDtoGetPostPut> postsToReturn = new ArrayList<>();
        List<Post> posts = postRepository.findPostByUserId(id);
        for (Post post : posts) {
            if(post.getStatus().getId() == 2){ //Status id = 2 -> Published
                PostDtoGetPostPut postDtoGetPostPut = new PostDtoGetPostPut();
                postDtoGetPostPut.convertToPostDTO(post);
                postsToReturn.add(postDtoGetPostPut);
            }
        }
        return postsToReturn;
    }

    //This method returns an Optionals of List<PostDtoGetPostPut>
    //Calls userRepository.findByUsername() to find user
    //If user exist, calls findPostsByAuthorId and returns the Optionals of List<PostDtoGetPostPut>
    //If there is not such author, returns an empty Optional
    public List<PostDtoGetPostPut> findPostsByAuthorUsername(String username){
        List<PostDtoGetPostPut> postsToReturn = new ArrayList<>();
        List<Post> posts = postRepository.findPostByUserUsername(username);
        for (Post post : posts) {
            if(post.getStatus().getId() == 2){ //Status id = 2 -> Published
                PostDtoGetPostPut postDtoGetPostPut = new PostDtoGetPostPut();
                postDtoGetPostPut.convertToPostDTO(post);
                postsToReturn.add(postDtoGetPostPut);
            }
        }
        return postsToReturn;
    }

    //This method returns a list of Optionals of PostDtoGetPostPut. This returns the list of published posts that belong to a category
    //Calls postRepository.findAll() and uses to nested for cycles to iterate over the posts and to add to the Arraylist to return
    public List<PostDtoGetPostPut> findPostsByCategoryId(long id){
        List<PostDtoGetPostPut> postsToReturn = new ArrayList<>();
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            if(post.getStatus().getId() == 2){ //Status id = 2 -> Published
                for(CategoriesPost categoriesPost : post.getCategoriesPosts()){
                    if(categoriesPost.getCategory().getId() == id){
                        PostDtoGetPostPut postDtoGetPostPut = new PostDtoGetPostPut();
                        postDtoGetPostPut.convertToPostDTO(post);
                        postsToReturn.add(postDtoGetPostPut);
                    }
                }
            }
        }
        return postsToReturn;
    }

    //This method returns an Optionals of List<PostDtoGetPostPut>
    //Calls categoryRepository.findByCategory() to find category
    //If category exist, calls findPostsByCategoryId and returns the Optional of List<PostDtoGetPostPut>
    //If there is not such category, returns an empty Optional
    public Optional<List<PostDtoGetPostPut>> findPostsByCategoryName(String categoryName){
        Optional<Category> category = categoryRepository.findByCategory(categoryName);
        if(category.isPresent()){
            return Optional.of(findPostsByCategoryId(category.get().getId()));
        }
        return Optional.empty();
    }

    //This method returns a list of Optionals of PostDtoGetPostPut. This returns the list of published posts that have a specific tag
    //Calls postRepository.findAll() and uses to nested for cycles to iterate over the posts and to add to the Arraylist to return
    public List<PostDtoGetPostPut> findPostsByTagId(long id){
        List<PostDtoGetPostPut> postsToReturn = new ArrayList<>();
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            if(post.getStatus().getId() == 2){ //Status id = 2 -> Published
                for(TagsPost tagsPost : post.getTagsPosts()){
                    if(tagsPost.getTag().getId() == id){ //Validate searched tag
                        PostDtoGetPostPut postDtoGetPostPut = new PostDtoGetPostPut();
                        postDtoGetPostPut.convertToPostDTO(post);
                        postsToReturn.add(postDtoGetPostPut);
                    }
                }
            }
        }
        return postsToReturn;
    }

    //This method returns an Optionals of List<PostDtoGetPostPut>
    //Calls tagRepository.findByTag() to find tag
    //If tag exist, calls findPostsByTagId and returns the Optional of List<PostDtoGetPostPut>
    //If there is not such tag, returns an empty Optional
    public Optional<List<PostDtoGetPostPut>> findPostsByTagName(String tagName){
        Optional<Tag> tag = tagRepository.findByTag(tagName);
        if(tag.isPresent()){
            return Optional.of(findPostsByTagId(tag.get().getId()));
        }
        return Optional.empty();
    }

    // This method returns an Optional of PostDTOGetPostPut
    // Using title, if the searched post exists, returns the optional, if not, returns an empty Optional
    public Optional<PostDtoGetPostPut> findByTitle(String title) {
        Optional<Post> post = postRepository.findPostByTitle(title);
        if (post.isPresent()) {
            if(post.get().getStatus().getId() == 2){ //Status id = 2 -> Published
                PostDtoGetPostPut postDTOGetPostPut = new PostDtoGetPostPut();
                postDTOGetPostPut.convertToPostDTO(post.get());
                return Optional.of(postDTOGetPostPut);
            }
        }
        return Optional.empty();
    }

    // This method finds all posts stored in the database and returns a list of PostDTOGetPostPut
    public List<PostDtoGetPostPut> findPublished() {
        List<PostDtoGetPostPut> postsToReturn = new ArrayList<>();
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            if (post.getStatus().getId() == 2){
                PostDtoGetPostPut postDTOGetPostPut = new PostDtoGetPostPut();
                postDTOGetPostPut.convertToPostDTO(post);
                postsToReturn.add(postDTOGetPostPut);
            }
        }
        return postsToReturn;
    }

    // This method finds all posts stored in database that contain provided String (content) an returns a list of PostDTOGetPostPut
    public List<PostDtoGetPostPut> findByContent(PostFindByContentDto postFindByContentDto) {
        List<PostDtoGetPostPut> postsToReturn = new ArrayList<>();
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            if(post.getContent().contains(postFindByContentDto.getContent())){
                PostDtoGetPostPut postDTOGetPostPut = new PostDtoGetPostPut();
                postDTOGetPostPut.convertToPostDTO(post);
                postsToReturn.add(postDTOGetPostPut);
            }
        }
        return postsToReturn;
    }

    //This method finds all published posts and returns a list of PostDTOGetPostPut sorted descending by
    //number of likes
    public List<PostDtoGetPostPut> findAllPopularityOrdered() {
        List<PostDtoGetPostPut> postsToReturn = findPublished();
        postsToReturn.sort(Comparator.comparingInt(PostDtoGetPostPut::getLikes).reversed());
        return postsToReturn;
    }

    //This method returns an Optional of List<PostDtoGetPostPut> or an empty Optional
    //Creates a date form dateSearchDto data
    //Calls postRepository.findAll() to find all posts
    //Iterates all over the post found and filtrates those that are published and if date matches with publication date adds that post to postsToReturn
    public Optional<List<PostDtoGetPostPut>> findPostsByDate(DateSearchDto dateSearchDto){
        String date = dateSearchDto.getYear() + "-" + dateSearchDto.getMonth() + "-" + (dateSearchDto.getDay());
        List<PostDtoGetPostPut> postsToReturn = new ArrayList<>();
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            if(post.getPublication() != null){ //This validates if the post is published.
                if(post.getPublication().toString().contains(date)){
                    System.out.println(post.getPublication().toString());
                    PostDtoGetPostPut postDTOGetPostPut = new PostDtoGetPostPut();
                    postDTOGetPostPut.convertToPostDTO(post);
                    postsToReturn.add(postDTOGetPostPut);
                }
            }
        }
        if(!postsToReturn.isEmpty()){
            return Optional.of(postsToReturn);
        }
        return Optional.empty();
    }

    // This method returns an Optional of PostDTOGetPostPut
    // First validates if the associated user and status exist
    // Creates a Post object, sets its attributes from PostDTO received as parameter and saves it by calling postRepository.save()
    // Uses that Post as an assistant to save calling the repository save() function
    // If the associated user or status does not exist, returns an empty Optional
    public Optional<PostDtoGetPostPut> save(PostDto postDTO) {
        Optional<User> user = userRepository.findById(postDTO.getUserId());
        Optional<Status> status = statusRepository.findById(postDTO.getStatusId());
        if (user.isPresent() && status.isPresent()) {
            Post post = new Post();
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());
            post.setUser(user.get());
            post.setStatus(status.get());
            post.setLikes(0);
            post.setDislikes(0);
            post.setCreation_date(LocalDateTime.now());
            post.setLastUpdateDate(LocalDateTime.now());
            if(post.getStatus().getId() == 2){
                post.setPublication(LocalDateTime.now());
            }
            PostDtoGetPostPut savedPost = new PostDtoGetPostPut();
            savedPost.convertToPostDTO(postRepository.save(post));
            return Optional.of(savedPost);
        } else {
            return Optional.empty();
        }
    }

    //This method returns an Optional that can be present or empty.
    //First, it tries to find the post by id, then, if the Optional post is present, sets the attributes and returns an Optional
    //If there is not a post identified by that id, returns an empty optional
    public Optional<PostDtoGetPostPut> update(long id, PostUpdateDTO postUpdateDTO) {
        Optional<Post> post = postRepository.findById(id);
        Optional<Status> status = statusRepository.findById(postUpdateDTO.getStatus_id());
        if (post.isPresent() && status.isPresent()) {
            Post updatedPosts = post.get();
            updatedPosts.setTitle(postUpdateDTO.getTitle());
            updatedPosts.setContent(postUpdateDTO.getContent());
            updatedPosts.setStatus(status.get());
            updatedPosts.setLastUpdateDate(LocalDateTime.now());
            if(updatedPosts.getStatus().getId() == 2){
                updatedPosts.setPublication(LocalDateTime.now());
            }else{
                updatedPosts.setPublication(null);
            }
            PostDtoGetPostPut postDtoGetPostPut = new PostDtoGetPostPut();
            postDtoGetPostPut.convertToPostDTO(postRepository.save(updatedPosts));
            return Optional.of(postDtoGetPostPut);
        } else {
            return Optional.empty();
        }
    }

    public Optional<PostDtoGetPostPut> updateLikesDislikes(long id, PostLikesDislikesDTO postLikesDislikesDTO) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post updatedPosts = post.get();
            updatedPosts.setLikes(postLikesDislikesDTO.getLikes());
            updatedPosts.setDislikes(postLikesDislikesDTO.getDislikes());
            PostDtoGetPostPut postDtoGetPostPut = new PostDtoGetPostPut();
            postDtoGetPostPut.convertToPostDTO(postRepository.save(updatedPosts));
            return Optional.of(postDtoGetPostPut);
        } else {
            return Optional.empty();
        }
    }

    // This method, validating the Optional in the if block, returns true if deletion was made or false if not
    public boolean deleteById(long id) {
        if (postRepository.findById(id).isPresent()) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
