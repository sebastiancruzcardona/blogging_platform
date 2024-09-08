package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.FollowedAuthors;
import com.eam.blogging_platform.repository.FollowedAuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowedAuthorsService {
    @Autowired//Singleton backwards for just one FollowedAuthorsRepository instance
    private FollowedAuthorsRepository followedAuthorsRepository;

    //This method brings out every followedauthor stored in database's table followedauthor
    public List<FollowedAuthors> findAll(){
        return followedAuthorsRepository.findAll();
    }

    //This method finds a specific followedAuthor searching by id
    public Optional<FollowedAuthors> findById(long id){
        return followedAuthorsRepository.findById(id);
    }

    //This method saves a new user in database's table user
    public FollowedAuthors save(FollowedAuthors followedAuthors){
        return followedAuthorsRepository.save(followedAuthors);
    }

    //This method deletes a specific followedauthor register by using its id
    public void deleteById(long id){
        followedAuthorsRepository.deleteById(id);
    }


}
