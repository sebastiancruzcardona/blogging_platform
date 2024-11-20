package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.dto.RoleDTO;
import com.eam.blogging_platform.dto.RoleDTOGetPostPut;
import com.eam.blogging_platform.dto.TagDto;
import com.eam.blogging_platform.dto.TagDtoGetPostPut;
import com.eam.blogging_platform.entity.Role;
import com.eam.blogging_platform.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired //Singleton backwards for just one RoleService instance
    private RoleService roleService;

    //This method refers to roleService.findAll() method. Brings out every role stored in database table role as a List of RoleDTOs
    @GetMapping
    public List<RoleDTOGetPostPut> getAllRoles() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    //This method calls the findById method from roleService that returns an Optional
    //Then, tries to map the Optional RoleDTOGetPostPut by using the .ok() function from ResponseEntity, for this the role has to be present
    //If the optional is empty, executes the orElseGet() implementing a ResponseEntity.notFound().build()
    //It is equivalent to writing:
        /*if(bankDTOGetPutPost.isPresent()){
          return ResponseEntity.ok(bankDTOGetPutPost);
        else{
          return ResponseEntity.notFound().build();
        }*/
    public ResponseEntity<RoleDTOGetPostPut> getRoleById(@PathVariable long id){
        Optional<RoleDTOGetPostPut> roleDTOGetPutPost = roleService.findById(id);
        return roleDTOGetPutPost.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //This method refers to roleService.save() method. Saves a new role in database table role
    @PostMapping
    public ResponseEntity<RoleDTOGetPostPut> createRole(@Valid @RequestBody RoleDTO roleDTO){
        Optional<RoleDTOGetPostPut> savedRole = roleService.save(roleDTO);
        return savedRole.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }
    
    @PutMapping("/{id}")
    //This method calls the update method from roleService that needs an id and a RoleDTO object and returns an Optional
    //Then, tries to map the Optional RoleDTOGetPostPut by using the .ok() function from ResponseEntity, for this the roleDTOGetPostPut has to be present
    //If the optional is empty, executes the orElseGet() implementing a ResponseEntity.notFound().build()
    public ResponseEntity<RoleDTOGetPostPut> updateRole(@PathVariable long id, @Valid @RequestBody RoleDTO roleDTO){
        Optional<RoleDTOGetPostPut> roleDTOGetPutPost = roleService.update(id, roleDTO);
        return roleDTOGetPutPost.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    //This method refers to roleService.findById() and roleService.deleteById() methods. Finds a specific role searching by id and deletes it
    //If the role is found, deletes it.
    //If there is not a role identified by that id, returns 404 Not Found Status
    @DeleteMapping("{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable long id){
        if(roleService.deleteById(id)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
