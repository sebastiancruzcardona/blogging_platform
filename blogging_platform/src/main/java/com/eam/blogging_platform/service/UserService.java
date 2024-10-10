package com.eam.blogging_platform.service;

import com.eam.blogging_platform.dto.UserDTO;
import com.eam.blogging_platform.dto.UserDTOGetPostPut;
import com.eam.blogging_platform.entity.Role;
import com.eam.blogging_platform.entity.User;
import com.eam.blogging_platform.repository.RoleRepository;
import com.eam.blogging_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
            user.setPassword(userDTO.getPassword());
            user.setCreationDate(LocalDateTime.now());
            user.setRole(role.get());
            UserDTOGetPostPut savedUser = new UserDTOGetPostPut();
            savedUser.convertToUserDTO(userRepository.save(user));
            return Optional.of(savedUser);
        }else{
            return Optional.empty();
        }
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
            userUpdate.setPassword(userDTO.getPassword());
            userUpdate.setRole(role.get());
            UserDTOGetPostPut userDTOGetPostPut = new UserDTOGetPostPut();
            userDTOGetPostPut.convertToUserDTO(userUpdate);
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
}
