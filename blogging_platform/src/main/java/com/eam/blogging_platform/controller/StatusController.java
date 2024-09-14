package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.entity.Status;
import com.eam.blogging_platform.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired // Singleton backwards for just one statusService instance
    private StatusService statusService;

    // This method refers to statusService.findAll() method. Brings out every Status stored in the database table status
    @GetMapping
    public List<Status> getAllStatuses() {
        return statusService.findAll();
    }

    // This method refers to statusService.findById() method. Finds a specific Status searching by id
    // If the Status is found, maps the ResponseEntity and returns a 200 OK Status.
    // If there is not a Status identified by that id, returns 404 Not Found Status
    @GetMapping("/{id}")
    public ResponseEntity<Status> getStatusById(@PathVariable long id) {
        Optional<Status> status = statusService.findById(id);
        return status.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // This method refers to statusService.save() method. Saves a new Status in the database table status
    @PostMapping
    public Status createStatus(@RequestBody Status status) {
        return statusService.save(status);
    }

    // This method refers to statusService.findById() and statusService.save() methods. Finds a specific Status by id and updates it
    // If the Status is found, sets the attributes to the Status in edition, saves to update, and returns a 200 OK Status.
    // If there is not a Status identified by that id, returns 404 Not Found Status
    @PutMapping("/{id}")
    public ResponseEntity<Status> updateStatus(@PathVariable long id, @RequestBody Status updatedStatusData) {
        Optional<Status> status = statusService.findById(id);
        if (status.isPresent()) {
            Status updatedStatus = status.get();
            updatedStatus.setStatus(updatedStatusData.getStatus());

            return ResponseEntity.ok(statusService.save(updatedStatus));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // This method refers to statusService.findById() and statusService.deleteById() methods. Finds a specific Status by id and deletes it
    // If the Status is found, deletes it.
    // If there is not a Status identified by that id, returns 404 Not Found Status
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable long id) {
        if (statusService.findById(id).isPresent()) {
            statusService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

