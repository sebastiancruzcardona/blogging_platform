package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.CategoriesPost;
import com.eam.blogging_platform.repository.CategoriesPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesPostService {

    @Autowired // Singleton backwards for just one CategoriesPostRepository instance
    private CategoriesPostRepository categoriesPostRepository;

    // This method brings out every categories post stored in database's table categories_post
    public List<CategoriesPost> findAll() {
        return categoriesPostRepository.findAll();
    }

    // This method finds a specific categories post searching by id
    public Optional<CategoriesPost> findById(long id) {
        return categoriesPostRepository.findById(id);
    }

    // This method saves a new categories post in database's table categories_post
    public CategoriesPost save(CategoriesPost categoriesPost) {
        return categoriesPostRepository.save(categoriesPost);
    }

    // This method deletes a specific categories post by using its id
    public void deleteById(long id) {
        categoriesPostRepository.deleteById(id);
    }
}

