package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.Category;
import com.eam.blogging_platform.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired // Singleton backwards for just one CategoryRepository instance
    private CategoryRepository categoryRepository;

    // This method brings out every category stored in database's table category
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    // This method finds a specific category searching by id
    public Optional<Category> findById(long id) {
        return categoryRepository.findById(id);
    }

    // This method saves a new category in database's table category
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    // This method deletes a specific category by using its id
    public void deleteById(long id) {
        categoryRepository.deleteById(id);
    }
}
