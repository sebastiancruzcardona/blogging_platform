package com.eam.blogging_platform.repository;

import com.eam.blogging_platform.entity.Comment;
import com.eam.blogging_platform.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByPostId (Long postId);
}
