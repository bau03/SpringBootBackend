package com.innova.repository;

import com.innova.model.Category;
import com.innova.model.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, String> {
Page<Content> findByCategory(Category category, Pageable pageable);
List<Content> findAllById(Integer contentId);

}