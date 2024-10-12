package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.dto.CategoriesPostDTO;
import com.eam.blogging_platform.dto.CategoriesPostDTOGetPostPut;
import com.eam.blogging_platform.entity.CategoriesPost;
import com.eam.blogging_platform.service.CategoriesPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categoriesPosts")
public class CategoriesPostController {
    @Autowired
    private CategoriesPostService categoriesPostService;

    /**
     * Retrieves all categories-post relationships from the database.
     * @return List of CategoriesPostDTOGetPostPut representing all categories-post relationships.
     */
    @GetMapping
    public List<CategoriesPostDTOGetPostPut> getAllCategoriesPosts() {
        return categoriesPostService.findAllCategoriesPosts();
    }

    /**
     * Retrieves a specific categories-post relationship by its ID.
     * @param id The ID of the categories-post relationship to retrieve.
     * @return ResponseEntity containing the CategoriesPostDTOGetPostPut if found, otherwise 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriesPostDTOGetPostPut> getCategoriesPostById(@PathVariable long id) {
        Optional<CategoriesPostDTOGetPostPut> categoriesPostDTO = categoriesPostService.findCategoriesPostById(id);
        return categoriesPostDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new categories-post relationship.
     * @param categoriesPostDTO The categories-post data to create.
     * @return ResponseEntity containing the created CategoriesPostDTOGetPostPut if successful, otherwise 400 Bad Request.
     */
    @PostMapping
    public ResponseEntity<CategoriesPostDTOGetPostPut> createCategoriesPost(@Valid @RequestBody CategoriesPostDTO categoriesPostDTO) {
        Optional<CategoriesPostDTOGetPostPut> savedCategoriesPost = categoriesPostService.saveCategoriesPost(categoriesPostDTO);
        return savedCategoriesPost.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Deletes a specific categories-post relationship by its ID.
     * @param id The ID of the categories-post relationship to delete.
     * @return ResponseEntity with status 200 OK if deleted, otherwise 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoriesPost(@PathVariable long id) {
        if (categoriesPostService.deleteCategoriesPostById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

