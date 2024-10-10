package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.entity.Role;
import com.eam.blogging_platform.service.RoleService;
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

    //This method refers to roleService.findAll() method. Brings out every RoleDTO stored in database table role as a List of roles
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.findAll();
    }

    //This method refers to roleService.findById() method. Finds a specific role searching by id
    //If the role is found, maps the ResponseEntity and returns a 200 OK Status.
    //If there is not a role identified by that id, returns 404 Not Found Status
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable long id){
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //This method refers to roleService.save() method. Saves a new role in database table role
    @PostMapping
    public Role createRole(@RequestBody Role role){
        return roleService.save(role);
    }

    //This method refers to roleService.findById() and roleService.save() methods. Finds a specific role searching by id and updates it
    //If the role is found, sets the attributes to the role in edition, saves to update and returns a 200 OK Status.
    //If there is not a role identified by that id, returns 404 Not Found Status
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable long id, @RequestBody Role roleDetails){
        Optional<Role> role = roleService.findById(id);
        if(role.isPresent()){
            Role updatedRole = role.get();
            updatedRole.setRole(roleDetails.getRole());
            updatedRole.setDescription(roleDetails.getDescription());
            return ResponseEntity.ok(roleService.save(updatedRole));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //This method refers to roleService.findById() and roleService.deleteById() methods. Finds a specific role searching by id and deletes it
    //If the role is found, deletes it.
    //If there is not a role identified by that id, returns 404 Not Found Status
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable long id){
        if(roleService.findById(id).isPresent()){
            roleService.deleteById(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
