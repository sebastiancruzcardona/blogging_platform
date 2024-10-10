package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.dto.UserDTO;
import com.eam.blogging_platform.dto.UserDTOGetPostPut;
import com.eam.blogging_platform.entity.User;
import com.eam.blogging_platform.service.UserService;
import jakarta.validation.Valid;
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
    public List<UserDTOGetPostPut> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    //This method calls the findById method from userService that returns an Optional
    //Then, tries to map the Optional userDTOGetPostPut by using the .ok() function from ResponseEntity, for this the account has to be present
    //If the optional is empty, executes the orElseGet() implementing a ResponseEntity.notFound().build()
    //It is equivalent to writing:
        /*if(userDTOGetPostPut.isPresent()){
          return ResponseEntity.ok(userDTOGetPostPut);
        else{
          return ResponseEntity.notFound().build();
        }*/
    public ResponseEntity<UserDTOGetPostPut> getUserById(@PathVariable long id){
        Optional<UserDTOGetPostPut> userDTOGetPostPut = userService.findById(id);
        return userDTOGetPostPut.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping
    //This method calls the save method from userService that needs an UserDTO object and returns an Optional
    //Then, tries to map the Optional userDTOGetPostPut by using the .ok() function from ResponseEntity, for this the accountDTOGetPostPut has to be present
    public ResponseEntity<UserDTOGetPostPut> createUser(@Valid @RequestBody UserDTO userDTO){
        Optional<UserDTOGetPostPut> userDTOGetPostPut = userService.save(userDTO);
        return userDTOGetPostPut.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    //This method calls the update method from userService that needs an id and a UserDTO object and returns an Optional
    //Then, tries to map the Optional userDTOGetPostPut by using the .ok() function from ResponseEntity, for this the userDTOGetPostPut has to be present
    //If the optional is empty, executes the orElseGet() implementing a ResponseEntity.notFound().build()
    public ResponseEntity<UserDTOGetPostPut> updateUser(@PathVariable long id, @Valid @RequestBody UserDTO userDTO){
        Optional<UserDTOGetPostPut> userDTOGetPostPut = userService.update(id, userDTO);
        return userDTOGetPostPut.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //This method refers to userService.findById() and userService.deleteById() methods. Finds a specific user searching by id and deletes it
    //If the user is found, deletes it.
    //If there is not a user identified by that id, returns 404 Not Found Status
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteAccount(@PathVariable long id){
        if(userService.deleteById(id)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
