package com.eam.blogging_platform.service;

import com.eam.blogging_platform.dto.CategoriesPostDTO;
import com.eam.blogging_platform.dto.CategoriesPostDTOGetPostPut;
import com.eam.blogging_platform.entity.CategoriesPost;
import com.eam.blogging_platform.entity.Category;
import com.eam.blogging_platform.entity.Post;
import com.eam.blogging_platform.repository.CategoriesPostRepository;
import com.eam.blogging_platform.repository.CategoryRepository;
import com.eam.blogging_platform.repository.PostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriesPostService {
    @Autowired
    private CategoriesPostRepository categoriesPostRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    /**
     * Retrieves all categories-post relationships from the database.
     * @return List of CategoriesPostDTOGetPostPut representing all categories-post relationships.
     */
    public List<CategoriesPostDTOGetPostPut> findAllCategoriesPosts() {
        List<CategoriesPostDTOGetPostPut> categoriesPostsToReturn = new ArrayList<>();
        List<CategoriesPost> categoriesPosts = categoriesPostRepository.findAll();
        for (CategoriesPost categoriesPost : categoriesPosts) {
            CategoriesPostDTOGetPostPut categoriesPostDTO = new CategoriesPostDTOGetPostPut();
            categoriesPostDTO.convertToCategoriesPostDTO(categoriesPost);
            categoriesPostsToReturn.add(categoriesPostDTO);
        }
        return categoriesPostsToReturn;
    }

    /**
     * Finds a categories-post relationship by its ID.
     * @param id The ID of the categories-post relationship.
     * @return Optional containing the CategoriesPostDTOGetPostPut if found, otherwise empty.
     */
    public Optional<CategoriesPostDTOGetPostPut> findCategoriesPostById(long id) {
        Optional<CategoriesPost> categoriesPost = categoriesPostRepository.findById(id);
        if (categoriesPost.isPresent()) {
            CategoriesPostDTOGetPostPut categoriesPostDTO = new CategoriesPostDTOGetPostPut();
            categoriesPostDTO.convertToCategoriesPostDTO(categoriesPost.get());
            return Optional.of(categoriesPostDTO);
        }
        return Optional.empty();
    }

    /**
     * Saves a new categories-post relationship in the database.
     * @param categoriesPostDTO The categories-post information to save.
     * @return Optional containing the saved CategoriesPostDTOGetPostPut if successful.
     */
    public Optional<CategoriesPostDTOGetPostPut> saveCategoriesPost(CategoriesPostDTO categoriesPostDTO) {
        Optional<Category> category = categoryRepository.findById(categoriesPostDTO.getCategoryId());
        Optional<Post> post = postRepository.findById(categoriesPostDTO.getPostId());
        if(category.isPresent() && post.isPresent()) {
            CategoriesPost categoriesPost = new CategoriesPost();
            categoriesPost.setPost(post.get()); // Replace with actual Post retrieval logic
            categoriesPost.setCategory(category.get()); // Replace with actual Category retrieval logic
            CategoriesPostDTOGetPostPut savedCategoriesPostDTO = new CategoriesPostDTOGetPostPut();
            savedCategoriesPostDTO.convertToCategoriesPostDTO(categoriesPostRepository.save(categoriesPost));
            return Optional.of(savedCategoriesPostDTO);
        }else {
            return Optional.empty();
        }
    }

    /**
     * Deletes a categories-post relationship by its ID.
     * @param id The ID of the categories-post relationship to delete.
     * @return True if the categories-post relationship was deleted, false otherwise.
     */
    public boolean deleteCategoriesPostById(long id) {
        if (categoriesPostRepository.findById(id).isPresent()) {
            categoriesPostRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

