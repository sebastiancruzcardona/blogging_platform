package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.entity.CategoriesPost;
import com.eam.blogging_platform.service.CategoriesPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories_posts")
public class CategoriesPostController {

    @Autowired // Singleton backwards for just one categoriesPostService instance
    private CategoriesPostService categoriesPostService;

    // This method refers to categoriesPostService.findAll() method. Brings out every CategoriesPost stored in the database table categories_post
    @GetMapping
    public List<CategoriesPost> getAllCategoriesPosts() {
        return categoriesPostService.findAll();
    }

    // This method refers to categoriesPostService.findById() method. Finds a specific CategoriesPost searching by id
    // If the CategoriesPost is found, maps the ResponseEntity and returns a 200 OK Status.
    // If there is not a CategoriesPost identified by that id, returns 404 Not Found Status
    @GetMapping("/{id}")
    public ResponseEntity<CategoriesPost> getCategoriesPostById(@PathVariable long id) {
        Optional<CategoriesPost> categoriesPost = categoriesPostService.findById(id);
        return categoriesPost.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // This method refers to categoriesPostService.save() method. Saves a new CategoriesPost in the database table categories_post
    @PostMapping
    public CategoriesPost createCategoriesPost(@RequestBody CategoriesPost categoriesPost) {
        return categoriesPostService.save(categoriesPost);
    }

    // This method refers to categoriesPostService.findById() and categoriesPostService.save() methods. Finds a specific CategoriesPost by id and updates it
    // If the CategoriesPost is found, sets the attributes to the CategoriesPost in edition, saves to update, and returns a 200 OK Status.
    // If there is not a CategoriesPost identified by that id, returns 404 Not Found Status
    @PutMapping("/{id}")
    public ResponseEntity<CategoriesPost> updateCategoriesPost(@PathVariable long id, @RequestBody CategoriesPost updatedCategoriesPostData) {
        Optional<CategoriesPost> categoriesPost = categoriesPostService.findById(id);
        if (categoriesPost.isPresent()) {
            CategoriesPost updatedCategoriesPost = categoriesPost.get();
            updatedCategoriesPost.setPost(updatedCategoriesPostData.getPost());
            updatedCategoriesPost.setCategory(updatedCategoriesPostData.getCategory());

            return ResponseEntity.ok(categoriesPostService.save(updatedCategoriesPost));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // This method refers to categoriesPostService.findById() and categoriesPostService.deleteById() methods. Finds a specific CategoriesPost by id and deletes it
    // If the CategoriesPost is found, deletes it.
    // If there is not a CategoriesPost identified by that id, returns 404 Not Found Status
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoriesPost(@PathVariable long id) {
        if (categoriesPostService.findById(id).isPresent()) {
            categoriesPostService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

