package com.eam.blogging_platform.repository;

import com.eam.blogging_platform.entity.TagsPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsPostRepository extends JpaRepository<TagsPost, Long> {
}
