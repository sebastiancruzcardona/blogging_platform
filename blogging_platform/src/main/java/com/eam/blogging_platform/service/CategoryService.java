package com.eam.blogging_platform.service;

import com.eam.blogging_platform.dto.CategoryDTOGetPostPut;
import com.eam.blogging_platform.entity.Category;
import com.eam.blogging_platform.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Retrieves all category records from the database.
     * @return List of CategoryDTOGetPostPut representing all categories.
     */
    public List<CategoryDTOGetPostPut> findAllCategories() {
        List<CategoryDTOGetPostPut> categoriesToReturn = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            CategoryDTOGetPostPut categoryDTO = new CategoryDTOGetPostPut();
            categoryDTO.convertToCategoryDTO(category);
            categoriesToReturn.add(categoryDTO);
        }
        return categoriesToReturn;
    }

    /**
     * Finds a category by its ID.
     * @param id The ID of the category.
     * @return Optional containing the CategoryDTOGetPostPut if found, otherwise empty.
     */
    public Optional<CategoryDTOGetPostPut> findCategoryById(long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            CategoryDTOGetPostPut categoryDTO = new CategoryDTOGetPostPut();
            categoryDTO.convertToCategoryDTO(category.get());
            return Optional.of(categoryDTO);
        }
        return Optional.empty();
    }

    /**
     * Saves a new category in the database.
     * @param categoryDTO The category information to save.
     * @return Optional containing the saved CategoryDTOGetPostPut if successful.
     */
    public Optional<CategoryDTOGetPostPut> saveCategory(CategoryDTOGetPostPut categoryDTO) {
        Category category = new Category();
        category.setCategory(categoryDTO.getCategory());
        category.setDescription(categoryDTO.getDescription());
        category.setCreationDate(LocalDateTime.now());
        CategoryDTOGetPostPut savedCategoryDTO = new CategoryDTOGetPostPut();
        savedCategoryDTO.convertToCategoryDTO(categoryRepository.save(category));
        return Optional.of(savedCategoryDTO);
    }

    /**
     * Updates an existing category by its ID.
     * @param id The ID of the category to update.
     * @param categoryDTO The updated category information.
     * @return Optional containing the updated CategoryDTOGetPostPut if successful.
     */
    public Optional<CategoryDTOGetPostPut> updateCategory(long id, CategoryDTOGetPostPut categoryDTO) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Category categoryToUpdate = category.get();
            categoryToUpdate.setCategory(categoryDTO.getCategory());
            categoryToUpdate.setDescription(categoryDTO.getDescription());
            CategoryDTOGetPostPut updatedCategoryDTO = new CategoryDTOGetPostPut();
            updatedCategoryDTO.convertToCategoryDTO(categoryRepository.save(categoryToUpdate));
            return Optional.of(updatedCategoryDTO);
        }
        return Optional.empty();
    }

    /**
     * Deletes a category by its ID.
     * @param id The ID of the category to delete.
     * @return True if the category was deleted, false otherwise.
     */
    public boolean deleteCategoryById(long id) {
        if (categoryRepository.findById(id).isPresent()) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
