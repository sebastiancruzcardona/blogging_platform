package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.entity.Category;
import com.eam.blogging_platform.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired // Singleton backwards for just one categoryService instance
    private CategoryService categoryService;

    // This method refers to categoryService.findAll() method. Brings out every Category stored in the database table category as a List of categories
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    // This method refers to categoryService.findById() method. Finds a specific category searching by id
    // If the category is found, maps the ResponseEntity and returns a 200 OK Status.
    // If there is not a category identified by that id, returns 404 Not Found Status
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable long id) {
        Optional<Category> category = categoryService.findById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // This method refers to categoryService.save() method. Saves a new category in the database table category
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.save(category);
    }

    // This method refers to categoryService.findById() and categoryService.save() methods. Finds a specific category by id and updates it
    // If the category is found, sets the attributes to the category in edition, saves to update, and returns a 200 OK Status.
    // If there is not a category identified by that id, returns 404 Not Found Status
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable long id, @RequestBody Category newCategoryData) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            Category updatedCategory = category.get();
            updatedCategory.setCategory(newCategoryData.getCategory());
            updatedCategory.setDescription(newCategoryData.getDescription());
            updatedCategory.setCreationDate(newCategoryData.getCreationDate());

            return ResponseEntity.ok(categoryService.save(updatedCategory));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // This method refers to categoryService.findById() and categoryService.deleteById() methods. Finds a specific category by id and deletes it
    // If the category is found, deletes it.
    // If there is not a category identified by that id, returns 404 Not Found Status
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
        if (categoryService.findById(id).isPresent()) {
            categoryService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

