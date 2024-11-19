package com.eam.blogging_platform.repository;

import com.eam.blogging_platform.entity.Category;
import com.eam.blogging_platform.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTag(String tag);
}
