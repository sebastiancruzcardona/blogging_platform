package com.eam.blogging_platform.repository;

import com.eam.blogging_platform.entity.FollowedAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowedAuthorsRepository extends JpaRepository<FollowedAuthor, Long> {
    List<FollowedAuthor> findFollowedAuthorsByFollowerId(Long followerId);
    List<FollowedAuthor> findFollowedAuthorsByAuthorId(Long authorId);
}
