package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.dto.*;
import com.eam.blogging_platform.entity.FollowedAuthor;
import com.eam.blogging_platform.entity.User;
import com.eam.blogging_platform.service.UserDetailsServiceImpl;
import com.eam.blogging_platform.service.UserService;
import com.eam.blogging_platform.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtUtil jwtUtil;

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

    @PostMapping("/register")
    //This method calls the save method from userService that needs an UserDTO object and returns an Optional
    //Then, tries to map the Optional userDTOGetPostPut by using the .ok() function from ResponseEntity, for this the accountDTOGetPostPut has to be present
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO){
        Optional<UserDTOGetPostPut> userDTOGetPostPut = userService.save(userDTO);
        return userDTOGetPostPut.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDTO.getUsername(), userLoginDTO.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userLoginDTO.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
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

    //FollowedAuthors methods

    //Get all followed authors
    @GetMapping("/followedAuthors")
    public List<FollowedAuthorDTOGetPostPut> getAllFollowedAuthors() {
        return userService.findAllFollowedAuthors();
    }

    //Get a specific followedAuthor by id
    @GetMapping("/followedAuthors/{id}")
    public ResponseEntity<FollowedAuthorDTOGetPostPut> getFollowedAuthorById(@PathVariable long id){
        Optional<FollowedAuthorDTOGetPostPut> followedAuthorDTOGetPostPut = userService.findFollowedAuthorById(id);
        return followedAuthorDTOGetPostPut.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    //Get the list of followedAuthors of a follower by its id
    @GetMapping("/followedAuthors/follower/{id}")
    public List<FollowedAuthorDTOGetPostPut> getFollowedAuthorByFollowerId(@PathVariable long id){
        return userService.findFollowedAuthorsByFollowerId(id);
    }

    //Get the list of followedAuthors (followers) of a specific author by ist id
    @GetMapping("/followedAuthors/author/{id}")
    public List<FollowedAuthorDTOGetPostPut> getFollowedAuthorByAuthorId(@PathVariable long id){
        return userService.findFollowedAuthorsByAuthorId(id);
    }

    //Create a new followedAuthor register
    @PostMapping("/followedAuthors")
    public ResponseEntity<FollowedAuthorDTOGetPostPut> createFollowedAuthor(@Valid @RequestBody FollowedAuthorDTO followedAuthorDTO){
        Optional<FollowedAuthorDTOGetPostPut> followedAuthorDTOGetPostPut = userService.saveFollowedAuthor(followedAuthorDTO);
        return followedAuthorDTOGetPostPut.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Delete followedAuthor by id
    @DeleteMapping("/followedAuthors/{id}")
    public ResponseEntity<FollowedAuthor> deleteFollowedAuthor(@PathVariable long id){
        if(userService.deleteFollowedAuthorById(id)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }


}
