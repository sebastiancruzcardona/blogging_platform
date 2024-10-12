package com.eam.blogging_platform.service;

import com.eam.blogging_platform.dto.*;
import com.eam.blogging_platform.entity.*;
import com.eam.blogging_platform.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    /**
     * Retrieves all status records from the database.
     * @return List of StatusDTOGetPostPut representing all statuses.
     */
    public List<StatusDTOGetPostPut> findAllStatuses() {
        List<StatusDTOGetPostPut> statusesToReturn = new ArrayList<>();
        List<Status> statuses = statusRepository.findAll();
        for (Status status : statuses) {
            StatusDTOGetPostPut statusDTO = new StatusDTOGetPostPut();
            statusDTO.convertToStatusDTO(status);
            statusesToReturn.add(statusDTO);
        }
        return statusesToReturn;
    }

    /**
     * Finds a status by its ID.
     * @param id The ID of the status.
     * @return Optional containing the StatusDTOGetPostPut if found, otherwise empty.
     */
    public Optional<StatusDTOGetPostPut> findStatusById(long id) {
        Optional<Status> status = statusRepository.findById(id);
        if (status.isPresent()) {
            StatusDTOGetPostPut statusDTO = new StatusDTOGetPostPut();
            statusDTO.convertToStatusDTO(status.get());
            return Optional.of(statusDTO);
        }
        return Optional.empty();
    }

    /**
     * Saves a new status in the database.
     * @param statusDTO The status information to save.
     * @return Optional containing the saved StatusDTOGetPostPut if successful.
     */
    public Optional<StatusDTOGetPostPut> saveStatus(StatusDTO statusDTO) {
        Status status = new Status();
        status.setStatus(statusDTO.getStatus());
        Status savedStatus = statusRepository.save(status);
        StatusDTOGetPostPut savedStatusDTO = new StatusDTOGetPostPut();
        savedStatusDTO.convertToStatusDTO(savedStatus);
        return Optional.of(savedStatusDTO);
    }

    /**
     * Updates an existing status by its ID.
     * @param id The ID of the status to update.
     * @param statusDTO The updated status information.
     * @return Optional containing the updated StatusDTOGetPostPut if successful.
     */
    public Optional<StatusDTOGetPostPut> updateStatus(long id, StatusDTO statusDTO) {
        Optional<Status> status = statusRepository.findById(id);
        if (status.isPresent()) {
            Status statusToUpdate = status.get();
            statusToUpdate.setStatus(statusDTO.getStatus());
            StatusDTOGetPostPut updatedStatusDTO = new StatusDTOGetPostPut();
            updatedStatusDTO.convertToStatusDTO(statusRepository.save(statusToUpdate));
            return Optional.of(updatedStatusDTO);
        }
        return Optional.empty();
    }

    /**
     * Deletes a status by its ID.
     * @param id The ID of the status to delete.
     * @return True if the status was deleted, false otherwise.
     */
    public boolean deleteStatusById(long id) {
        if (statusRepository.findById(id).isPresent()) {
            statusRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

