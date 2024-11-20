package com.eam.blogging_platform.repository;

import com.eam.blogging_platform.entity.CategoriesPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CategoriesPostRepository extends JpaRepository<CategoriesPost, Long>{
    Optional<CategoriesPost> findByPostIdAndCategoryId(Long postId, Long categoryId);
    List<CategoriesPost> findByPostId(Long postId);
}
