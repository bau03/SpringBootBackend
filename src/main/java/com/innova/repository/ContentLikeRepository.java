package com.innova.repository;

import com.innova.model.ContentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContentLikeRepository extends JpaRepository<ContentLike, Integer> {

}