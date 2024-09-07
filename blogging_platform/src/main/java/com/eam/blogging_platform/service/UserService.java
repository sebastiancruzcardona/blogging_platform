package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.User;
import com.eam.blogging_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired//Singleton backwards for just one UserRepository instance
    private UserRepository userRepository;

    //This method brings out every user stored in database's table user
    public List<User> findAll(){
        return userRepository.findAll();
    }

    //This method finds a specific user searching by id
    public Optional<User> findById(long id){
        return userRepository.findById(id);
    }

    //This method saves a new user in database's table user
    public User save(User user){
        return userRepository.save(user);
    }

    //This method deletes a specific user by using its id
    public void deleteById(long id){
        userRepository.deleteById(id);
    }
}
