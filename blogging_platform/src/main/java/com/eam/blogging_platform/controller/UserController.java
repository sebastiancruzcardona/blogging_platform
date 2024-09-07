package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.entity.User;
import com.eam.blogging_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired //Singleton backwards for just one UserService instance
    private UserService userService;

    //This method refers to userService.findAll() method. Brings out every user stored in database's table user as a List of users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    //This method refers to userService.findById() method. Finds a specific user searching by id
    //If the user is found, maps the ResponseEntity and returns a 200 OK Status.
    //If there is not a user identified by that id, returns 404 Not Found Status
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    //This method refers to userService.save() method. Saves a new user in database's table role
    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.save(user);
    }

    //This method refers to userService.findById() and userService.save() methods. Finds a specific user searching by id and updates it
    //If the user is found, sets the attributes to the role in edition, saves to update and returns a 200 OK Status.
    //If there is not a user identified by that id, returns 404 Not Found Status
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User userDetails){
        Optional<User> user = userService.findById(id);
        if(user.isPresent()){
            User updatedUser = user.get();
            updatedUser.setUsername(userDetails.getUsername());
            updatedUser.setEmail(userDetails.getEmail());
            updatedUser.setPassword(userDetails.getPassword());
            updatedUser.setRole(userDetails.getRole()); //IS IT CORRECT TO UPDATE THE FOREIGN KEY?
            return ResponseEntity.ok(userService.save(updatedUser));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //This method refers to userService.findById() and userService.deleteById() methods. Finds a specific user searching by id and deletes it
    //If the user is found, deletes it.
    //If there is not a user identified by that id, returns 404 Not Found Status
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        if(userService.findById(id).isPresent()){
            userService.delete(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
