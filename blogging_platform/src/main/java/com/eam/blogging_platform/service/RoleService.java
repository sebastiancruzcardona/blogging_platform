package com.eam.blogging_platform.service;

import com.eam.blogging_platform.dto.RoleDTO;
import com.eam.blogging_platform.dto.RoleDTOGetPostPut;
import com.eam.blogging_platform.entity.Role;
import com.eam.blogging_platform.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired //Singleton backwards for just one RoleRepository instance
    private RoleRepository roleRepository;

    //This method finds all roles stored in database and returns a list of RoleDTOGetPostPut
    //Calls roleRepository.findAll() and uses a for cycle to iterate over the roles and to add to the Arraylist to return
    public List<RoleDTOGetPostPut> findAll() {
        List<RoleDTOGetPostPut> rolesToReturn = new ArrayList<>();
        List<Role> roles = roleRepository.findAll();
        for (Role role : roles) {
            RoleDTOGetPostPut roleDTOGetPostPut = new RoleDTOGetPostPut();
            roleDTOGetPostPut.convertToRoleDTO(role);
            rolesToReturn.add(roleDTOGetPostPut);
        }
        return rolesToReturn;
    }

    //This method returns an Optional of RoleDTOGetPostPut
    //Using id, if the searched role exist, returns the optional, if not, returns an empty Optional
    public Optional<RoleDTOGetPostPut> findById(long id) {
        Optional<Role> role = roleRepository.findById(id);
        if(role.isPresent()) {
            RoleDTOGetPostPut roleDTOGetPutPost = new RoleDTOGetPostPut();
            roleDTOGetPutPost.convertToRoleDTO(role.get());
            return Optional.of(roleDTOGetPutPost);
        }
        return Optional.empty();
    }

    //This method returns RoleDTOGetPostPut object
    //Creates a role object, sets its attributes from RoleDTOGetPostPut received as parameter and saves it by calling roleRepository.save()
    //Uses that Role as an assistant to save calling the repository save() function
    public RoleDTOGetPostPut save(RoleDTO roleDTO) {
        Role role = new Role();
        role.setRole(roleDTO.getRole());
        role.setDescription(roleDTO.getDescription());
        RoleDTOGetPostPut roleDTOGetPostPut = new RoleDTOGetPostPut();
        roleDTOGetPostPut.convertToRoleDTO(roleRepository.save(role));
        return roleDTOGetPostPut;
    }

    //This method returns an Optional that can be present or empty.
    //First, it tries to find the Role by id, then, if the Optional role is present, sets the attributes and returns an Optional
    //If there is not a role identified by that id, returns an empty optional
    public Optional<RoleDTOGetPostPut> update(long id, RoleDTO roleDTO){
        Optional<Role> role = roleRepository.findById(id);
        if(role.isPresent()){
            Role updatedrole = role.get();
            updatedrole.setRole(roleDTO.getRole());
            updatedrole.setDescription(roleDTO.getDescription());
            RoleDTOGetPostPut roleDTOGetPostPut = new RoleDTOGetPostPut();
            roleDTOGetPostPut.convertToRoleDTO(roleRepository.save(updatedrole));
            return Optional.of(roleDTOGetPostPut);
        }else{
            return Optional.empty();
        }
    }

    //This method, validating the Optional in the if block, returns true if deletion was made or false if not
    public boolean deleteById(long id) {
        if(roleRepository.findById(id).isPresent()) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
