package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.entity.FollowedAuthors;
import com.eam.blogging_platform.entity.User;
import com.eam.blogging_platform.service.FollowedAuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/followedAuthors")
public class FollowedAuthorsController {
    @Autowired //Singleton backwards for just one UserService instance
    private FollowedAuthorsService followedAuthorsService;

    //This method refers to followedAuthorsService.findAll() method. Brings out every followedAuthor register
    //stored in database's table followedAuthors as a List of FollowedAuthors
    @GetMapping
    public List<FollowedAuthors> getAllFollowedAuthors() {
        return followedAuthorsService.findAll();
    }

    //This method refers to followedAuthorsService.findById() method. Finds a specific followedAuthors register searching by id
    //If the followedAuthor register is found, maps the ResponseEntity and returns a 200 OK Status.
    //If there is not a followedAuthor register identified by that id, returns 404 Not Found Status
    @GetMapping("/{id}")
    public ResponseEntity<FollowedAuthors> getFollowedAuthorById(@PathVariable long id) {
        Optional<FollowedAuthors> followedAuthorRegister = followedAuthorsService.findById(id);
        return followedAuthorRegister.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //This method refers to followedAuthorsService.save() method. Saves a new followedAuthor register in database's table followedAuthors
    @PostMapping
    public FollowedAuthors createFollowedAuthor(@RequestBody FollowedAuthors followedAuthor) {
        return followedAuthorsService.save(followedAuthor);
    }

    //This method refers to followedAuthorsService.findById() and followedAuthorsService.save() methods. Finds a specific followedAutor register searching by id and updates it
    //If the followedAutor register is found, sets the attributes and saves to update, and returns a 200 OK Status.
    //If there is not a followedAutor register identified by that id, returns 404 Not Found Status
    @PutMapping("/{id}")
    public ResponseEntity<FollowedAuthors> updateFollowedAuthor(@PathVariable long id, @RequestBody FollowedAuthors followedAuthorDetails) {
        Optional<FollowedAuthors> followedAuthorRegister = followedAuthorsService.findById(id);
        if(followedAuthorRegister.isPresent()){
            FollowedAuthors updatedFollowerAuthorRegister = followedAuthorRegister.get();
            updatedFollowerAuthorRegister.setAuthor(followedAuthorDetails.getAuthor());
            updatedFollowerAuthorRegister.setFollower(followedAuthorDetails.getFollower());
            return ResponseEntity.ok(followedAuthorsService.save(updatedFollowerAuthorRegister));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //This method refers to followedAuthorsService.findById() and followedAuthorsService.deleteById() methods. Finds a specific followedAuthor register searching by id and deletes it
    //If the followedAuthor register is found, deletes it.
    //If there is not a followedAuthor register identified by that id, returns 404 Not Found Status
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFollowedAuthor(@PathVariable long id) {
        if(followedAuthorsService.findById(id).isPresent()){
            followedAuthorsService.deleteById(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }


}
