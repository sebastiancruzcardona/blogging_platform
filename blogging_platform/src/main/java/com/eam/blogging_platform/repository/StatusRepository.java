package com.eam.blogging_platform.repository;

import com.eam.blogging_platform.entity.Status;
import com.eam.blogging_platform.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long>{
    Optional<Status> findByStatus(String status);
}
