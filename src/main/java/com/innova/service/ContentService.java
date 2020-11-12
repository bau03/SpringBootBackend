package com.innova.service;

import com.innova.dto.request.ContentForm;
import com.innova.model.Category;
import com.innova.model.Content;
import com.innova.model.User;
import com.innova.repository.CategoryRepository;
import com.innova.repository.ContentRepository;
import com.innova.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentService {
    @Autowired
    ContentRepository contentRepository;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryServices categoryServices;

    public void save(ContentForm contentForm){
        Content content=new Content(contentForm.getContent());
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Category category= categoryServices.getCategory(contentForm.getCategoryId());
        content.setCategory(category);
        content.setUser(user);
        content.setTimestap(new Date());
        contentRepository.save(content);
    }

    public Page<Content> getContentPage(Pageable pageable){
        return contentRepository.findAll(pageable);
    }
    public Page<Content> getCategoryContentPage(String categoryName,Pageable pageable){
        Category category=categoryServices.getCategory(categoryName);
        return contentRepository.findByCategory(category,pageable);
    }
}
