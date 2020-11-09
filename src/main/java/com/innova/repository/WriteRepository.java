package com.innova.repository;

import com.innova.model.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriteRepository  extends JpaRepository<Writer, String> {

}
