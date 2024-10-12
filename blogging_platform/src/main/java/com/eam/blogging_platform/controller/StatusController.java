package com.eam.blogging_platform.controller;

import com.eam.blogging_platform.dto.StatusDTO;
import com.eam.blogging_platform.dto.StatusDTOGetPostPut;
import com.eam.blogging_platform.service.StatusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {
    @Autowired
    private StatusService statusService;

    /**
     * Retrieves all statuses from the database.
     * @return List of StatusDTOGetPostPut representing all statuses.
     */
    @GetMapping
    public List<StatusDTOGetPostPut> getAllStatuses() {
        return statusService.findAllStatuses();
    }

    /**
     * Retrieves a specific status by its ID.
     * @param id The ID of the status to retrieve.
     * @return ResponseEntity containing the StatusDTOGetPostPut if found, otherwise 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StatusDTOGetPostPut> getStatusById(@PathVariable long id) {
        Optional<StatusDTOGetPostPut> statusDTO = statusService.findStatusById(id);
        return statusDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new status.
     * @param statusDTO The status data to create.
     * @return ResponseEntity containing the created StatusDTOGetPostPut if successful, otherwise 400 Bad Request.
     */
    @PostMapping
    public ResponseEntity<StatusDTOGetPostPut> createStatus(@Valid @RequestBody StatusDTO statusDTO) {
        Optional<StatusDTOGetPostPut> savedStatus = statusService.saveStatus(statusDTO);
        return savedStatus.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Updates an existing status by its ID.
     * @param id The ID of the status to update.
     * @param statusDTO The updated status data.
     * @return ResponseEntity containing the updated StatusDTOGetPostPut if successful, otherwise 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StatusDTOGetPostPut> updateStatus(@PathVariable long id, @Valid @RequestBody StatusDTO statusDTO) {
        Optional<StatusDTOGetPostPut> updatedStatus = statusService.updateStatus(id, statusDTO);
        return updatedStatus.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes a specific status by its ID.
     * @param id The ID of the status to delete.
     * @return ResponseEntity with status 200 OK if deleted, otherwise 404 Not Found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable long id) {
        if (statusService.deleteStatusById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


