package com.eam.blogging_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eam.blogging_platform.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
