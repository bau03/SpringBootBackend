package com.innova.repository;

import com.innova.model.Comment;
import com.innova.model.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Page<Comment> findByContent(Content content, Pageable pageable);
    Comment findById(int id);
}
