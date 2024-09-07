package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.Role;
import com.eam.blogging_platform.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired //Singleton backwards for just one RoleRepository instance
    private RoleRepository roleRepository;

    //This method brings out every Role stored in table role in database
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    //This method finds a specific role searching by id
    public Optional<Role> findById(long id) {
        return roleRepository.findById(id);
    }

    //This method saves a new role in database table role
    public Role save(Role role){
        return roleRepository.save(role);
    }

    //This method deletes a specific role by using its id
    public void deleteById(long id){
        roleRepository.deleteById(id);
    }
}
