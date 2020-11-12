package com.innova.service;

import com.innova.model.Category;
import com.innova.model.Content;
import com.innova.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServices {

    @Autowired
    CategoryRepository categoryRepository;

    public Category getCategory(String id) {
        Category category = categoryRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User cannot find"));
        return category;
    }

    public List<Category> getCategoryDetail(){
        return categoryRepository.findAll();
    }

}
