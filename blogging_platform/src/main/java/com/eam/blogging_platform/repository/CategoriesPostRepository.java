package com.eam.blogging_platform.repository;

import com.eam.blogging_platform.entity.CategoriesPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesPostRepository extends JpaRepository<CategoriesPost, Long>{
}
