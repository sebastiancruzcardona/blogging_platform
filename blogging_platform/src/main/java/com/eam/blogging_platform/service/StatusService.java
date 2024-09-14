package com.eam.blogging_platform.service;

import com.eam.blogging_platform.entity.Status;
import com.eam.blogging_platform.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    @Autowired // Singleton backwards for just one StatusRepository instance
    private StatusRepository statusRepository;

    // This method brings out every status stored in database's table status
    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    // This method finds a specific status searching by id
    public Optional<Status> findById(long id) {
        return statusRepository.findById(id);
    }

    // This method saves a new status in database's table status
    public Status save(Status status) {
        return statusRepository.save(status);
    }

    // This method deletes a specific status by using its id
    public void deleteById(long id) {
        statusRepository.deleteById(id);
    }
}

