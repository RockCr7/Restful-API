package com.blog.blogapp.repository;

import com.blog.blogapp.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments,Long> {
    List<Comments> findByPostId(long postId);
}
