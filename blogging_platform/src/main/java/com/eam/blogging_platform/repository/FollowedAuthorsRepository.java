package com.eam.blogging_platform.repository;

import com.eam.blogging_platform.entity.FollowedAuthors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowedAuthorsRepository extends JpaRepository<FollowedAuthors, Long> {
}
