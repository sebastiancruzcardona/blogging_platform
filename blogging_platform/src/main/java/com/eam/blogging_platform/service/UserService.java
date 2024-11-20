package com.eam.blogging_platform.service;

import com.eam.blogging_platform.dto.*;
import com.eam.blogging_platform.entity.FollowedAuthor;
import com.eam.blogging_platform.entity.Role;
import com.eam.blogging_platform.entity.User;
import com.eam.blogging_platform.repository.FollowedAuthorsRepository;
import com.eam.blogging_platform.repository.RoleRepository;
import com.eam.blogging_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired//Singleton backwards for just one UserRepository instance
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FollowedAuthorsRepository followedAuthorsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; //Encrypter

    //This method finds all users stored in database and returns a list of UserDTOGetPostPut
    //Calls userRepository.findAll() and uses a for cycle to iterate over the users and to add to the Arraylist to return
    public List<UserDTOGetPostPut> findAll(){
        List<UserDTOGetPostPut> usersToReturn = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            UserDTOGetPostPut userDTOGetPostPut = new UserDTOGetPostPut();
            userDTOGetPostPut.convertToUserDTO(user);
            usersToReturn.add(userDTOGetPostPut);
        }
        return usersToReturn;
    }

    //This method returns an Optional of UserDTOGetPostPut
    //Using id, if the searched user exist, returns the optional, if not, returns an empty Optional
    public Optional<UserDTOGetPostPut> findById(long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            UserDTOGetPostPut userDTOGetPostPut = new UserDTOGetPostPut();
            userDTOGetPostPut.convertToUserDTO(user.get());
            return Optional.of(userDTOGetPostPut);
        }
        return Optional.empty();
    }

    //This method returns an Optional of UserDTOGetPostPut
    //First validates if the associated role exist
    //Creates a User object, sets its attributes from UserDTO received as parameter and saves it by calling userRepository.save()
    //Uses that User as an assistant to save calling the repository save() function
    //If the associated role does not exist returns an empty Optional
    public Optional<UserDTOGetPostPut> save(UserDTO userDTO){
        Optional<Role> role = roleRepository.findById(userDTO.getRoleID());
        if(role.isPresent()){
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword())); //Encrypt password
            user.setCreationDate(LocalDateTime.now());
            user.setRole(role.get());
            UserDTOGetPostPut savedUser = new UserDTOGetPostPut();
            savedUser.convertToUserDTO(userRepository.save(user));
            return Optional.of(savedUser);
        }else{
            return Optional.empty();
        }
    }

    //This method returns an Optional of UserDTOGetPostPut
    //Creates a User object, sets its attributes from UserRegisterUpdateDTO received as parameter and saves it by calling userRepository.save()
    //Uses that User as an assistant to save calling the repository save() function
    //Sets role author by default
    public Optional<UserDTOGetPostPut> saveForUser(UserRegisterUpdateDTO userRegisterUpdateDTO){
        User user = new User();
        user.setUsername(userRegisterUpdateDTO.getUsername());
        user.setEmail(userRegisterUpdateDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterUpdateDTO.getPassword())); //Encrypt password
        user.setCreationDate(LocalDateTime.now());
        Optional<Role> role = roleRepository.findByRole("author");
        role.ifPresent(user::setRole);
        UserDTOGetPostPut savedUser = new UserDTOGetPostPut();
        savedUser.convertToUserDTO(userRepository.save(user));
        return Optional.of(savedUser);
    }

    //This method returns an Optional of UserDTOGetPostPut that can be present or empty.
    //First, it tries to find the user by id and the role by id, then, if the Optionals user and role are present, sets the attributes and returns an Optional
    //If there is not a user identified by that id, and/or the role does not exist, returns an empty optional
    public Optional<UserDTOGetPostPut> update(long id, UserDTO userDTO){
        Optional<User> user = userRepository.findById(id);
        Optional<Role> role = roleRepository.findById(userDTO.getRoleID());
        if(user.isPresent() && role.isPresent()){
            User userUpdate = user.get();
            userUpdate.setUsername(userDTO.getUsername());
            userUpdate.setEmail(userDTO.getEmail());
            userUpdate.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userUpdate.setRole(role.get());
            UserDTOGetPostPut userDTOGetPostPut = new UserDTOGetPostPut();
            userDTOGetPostPut.convertToUserDTO(userRepository.save(userUpdate));
            return Optional.of(userDTOGetPostPut);
        }
        return Optional.empty();
    }

    //This method returns an Optional of UserDTOGetPostPut that can be present or empty.
    //First, it tries to find the user by id, then, if the Optional user is present, sets the attributes and returns an Optional
    //If there is not a user identified by that id, returns an empty optional
    public Optional<UserDTOGetPostPut> updateForUser(long id, UserRegisterUpdateDTO userRegisterUpdateDTO){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            User userUpdate = user.get();
            userUpdate.setUsername(userRegisterUpdateDTO.getUsername());
            userUpdate.setEmail(userRegisterUpdateDTO.getEmail());
            userUpdate.setPassword(passwordEncoder.encode(userRegisterUpdateDTO.getPassword()));
            UserDTOGetPostPut userDTOGetPostPut = new UserDTOGetPostPut();
            userDTOGetPostPut.convertToUserDTO(userRepository.save(userUpdate));
            return Optional.of(userDTOGetPostPut);
        }
        return Optional.empty();
    }

    //This method, validating the Optional in the if block, returns true if deletion was made or false if not
    public boolean deleteById(long id){
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //FollowedAuthor methods

    //This method finds all FollowedAuthors stored in database and returns a list of FollowedAuthorDTOGetPostPut
    //Calls userRepository.findAll() and uses a for cycle to iterate over the users and to add to the Arraylist to return
    public List<FollowedAuthorDTOGetPostPut> findAllFollowedAuthors(){
        List<FollowedAuthorDTOGetPostPut> followedAuthorsToReturn = new ArrayList<>();
        List<FollowedAuthor> followedAuthors = followedAuthorsRepository.findAll();
        for (FollowedAuthor followedAuthor : followedAuthors) {
            FollowedAuthorDTOGetPostPut followedAuthorDTOGetPostPut = new FollowedAuthorDTOGetPostPut();
            followedAuthorDTOGetPostPut.convertToFollowedAuthorDTO(followedAuthor);
            followedAuthorsToReturn.add(followedAuthorDTOGetPostPut);
        }
        return followedAuthorsToReturn;
    }

    //This method returns an Optional of FollowedAuthorDTOGetPostPut
    //Using id, if the searched user exist, returns the optional, if not, returns an empty Optional
    public Optional<FollowedAuthorDTOGetPostPut> findFollowedAuthorById(long id){
        Optional<FollowedAuthor> followedAuthor = followedAuthorsRepository.findById(id);
        if(followedAuthor.isPresent()){
            FollowedAuthorDTOGetPostPut followedAuthorDTOGetPostPut = new FollowedAuthorDTOGetPostPut();
            followedAuthorDTOGetPostPut.convertToFollowedAuthorDTO(followedAuthor.get());
            return Optional.of(followedAuthorDTOGetPostPut);
        }
        return Optional.empty();
    }

    //This method returns an Optional of FollowedAuthorDTOGetPostPut. This returns the list of follows of a follower
    //Calls followedAuthorsRepository.findFollowedAuthorsByFollowerId() and uses a for cycle to iterate over the users and to add to the Arraylist to return
    public List<FollowedAuthorDTOGetPostPut> findFollowedAuthorsByFollowerId(long id){
        List<FollowedAuthorDTOGetPostPut> followedAuthorsToReturn = new ArrayList<>();
        List<FollowedAuthor> followedAuthors = followedAuthorsRepository.findFollowedAuthorsByFollowerId(id);
        for (FollowedAuthor followedAuthor : followedAuthors) {
            FollowedAuthorDTOGetPostPut followedAuthorDTOGetPostPut = new FollowedAuthorDTOGetPostPut();
            followedAuthorDTOGetPostPut.convertToFollowedAuthorDTO(followedAuthor);
            followedAuthorsToReturn.add(followedAuthorDTOGetPostPut);
        }
        return followedAuthorsToReturn;
    }

    //This method returns an Optional of FollowedAuthorDTOGetPostPut. This returns the list of follows of an author
    //Calls followedAuthorsRepository.findFollowedAuthorsByAuthorId() and uses a for cycle to iterate over the users and to add to the Arraylist to return
    public List<FollowedAuthorDTOGetPostPut> findFollowedAuthorsByAuthorId(long id){
        List<FollowedAuthorDTOGetPostPut> followedAuthorsToReturn = new ArrayList<>();
        List<FollowedAuthor> followedAuthors = followedAuthorsRepository.findFollowedAuthorsByAuthorId(id);
        for (FollowedAuthor followedAuthor : followedAuthors) {
            FollowedAuthorDTOGetPostPut followedAuthorDTOGetPostPut = new FollowedAuthorDTOGetPostPut();
            followedAuthorDTOGetPostPut.convertToFollowedAuthorDTO(followedAuthor);
            followedAuthorsToReturn.add(followedAuthorDTOGetPostPut);
        }
        return followedAuthorsToReturn;
    }

    //This method returns an Optional of FollowedAuthorDTOGetPostPut
    //First validates if the associated follower and author exist
    //Creates a FollowedAuthor object, sets its attributes from FollowedAuthorDTO received as parameter and saves it by calling followedAuthorsRepository.save()
    //Uses that FollowedAuthor as an assistant to save calling the repository save() function
    //If the associated follower and author do not exist, returns an empty Optional
    public Optional<FollowedAuthorDTOGetPostPut> saveFollowedAuthor(FollowedAuthorDTO followedAuthorDTO){
        if(followedAuthorsRepository.findByFollowerIdAndAuthorId(followedAuthorDTO.getFollowerId(), followedAuthorDTO.getAuthorId()).isPresent()){
            return Optional.empty(); //Return an empty Optional if the followedAuthor already exist
        }
        Optional<User> follower = userRepository.findById(followedAuthorDTO.getFollowerId());
        Optional<User> author = userRepository.findById(followedAuthorDTO.getAuthorId());
        if(follower.isPresent() && author.isPresent()){
            FollowedAuthor followedAuthor = new FollowedAuthor();
            followedAuthor.setFollower(follower.get());
            followedAuthor.setAuthor(author.get());
            followedAuthor.setCreationDate(LocalDateTime.now());
            FollowedAuthorDTOGetPostPut savedFollowerAuthor = new FollowedAuthorDTOGetPostPut();
            savedFollowerAuthor.convertToFollowedAuthorDTO(followedAuthorsRepository.save(followedAuthor));
            return Optional.of(savedFollowerAuthor);
        }else{
            return Optional.empty();
        }
    }

    //This method, validating the Optional in the if block, returns true if deletion was made or false if not
    public boolean deleteFollowedAuthorById(long id){
        if(followedAuthorsRepository.findById(id).isPresent()){
            followedAuthorsRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
