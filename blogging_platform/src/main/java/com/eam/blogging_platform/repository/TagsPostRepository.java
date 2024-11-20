package com.eam.blogging_platform.repository;

import com.eam.blogging_platform.entity.TagsPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagsPostRepository extends JpaRepository<TagsPost, Long> {
    Optional<TagsPost> findByPostIdAndTagId(Long postId, Long tagId);
    List<TagsPost> findByPostId(Long postId);
}
