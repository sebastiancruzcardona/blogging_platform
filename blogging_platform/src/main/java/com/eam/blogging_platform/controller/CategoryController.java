package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.dto.CategoryDTOGetPostPut;
import com.eam.blogging_platform.entity.Category;
import com.eam.blogging_platform.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * Retrieves all categories from the database.
     * @return List of CategoryDTOGetPostPut representing all categories.
     */
    @GetMapping
    public List<CategoryDTOGetPostPut> getAllCategories() {
        return categoryService.findAllCategories();
    }

    /**
     * Retrieves a specific category by its ID.
     * @param id The ID of the category to retrieve.
     * @return ResponseEntity containing the CategoryDTOGetPostPut if found, otherwise 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTOGetPostPut> getCategoryById(@PathVariable long id) {
        Optional<CategoryDTOGetPostPut> categoryDTO = categoryService.findCategoryById(id);
        return categoryDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new category.
     * @param categoryDTO The category data to create.
     * @return ResponseEntity containing the created CategoryDTOGetPostPut if successful, otherwise 400 Bad Request.
     */
    @PostMapping
    public ResponseEntity<CategoryDTOGetPostPut> createCategory(@Valid @RequestBody CategoryDTOGetPostPut categoryDTO) {
        Optional<CategoryDTOGetPostPut> savedCategory = categoryService.saveCategory(categoryDTO);
        return savedCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Updates an existing category by its ID.
     * @param id The ID of the category to update.
     * @param categoryDTO The updated category data.
     * @return ResponseEntity containing the updated CategoryDTOGetPostPut if successful, otherwise 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTOGetPostPut> updateCategory(@PathVariable long id, @Valid @RequestBody CategoryDTOGetPostPut categoryDTO) {
        Optional<CategoryDTOGetPostPut> updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return updatedCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes a specific category by its ID.
     * @param id The ID of the category to delete.
     * @return ResponseEntity with status 200 OK if deleted, otherwise 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
        if (categoryService.deleteCategoryById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

