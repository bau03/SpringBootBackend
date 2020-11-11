package com.innova.service;

import com.innova.model.Category;
import com.innova.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServices {

    @Autowired
    CategoryRepository categoryRepository;

    public Category getCategory(String id) {
        System.out.println("categoryservisid"+id);
        Category category = categoryRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User cannot find"));
        return category;
    }
}
