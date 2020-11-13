package com.innova.service;

import com.innova.dto.request.ContentForm;
import com.innova.model.*;
import com.innova.repository.*;
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

    @Autowired
    ContentLikeRepository contentLikeRepository;

    @Autowired
    ContentDislikeRepository contentDislikeRepository;

    public void save(ContentForm contentForm){
        Content content=new Content(contentForm.getContent(),contentForm.getContentHeader());
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
    public List<Content> getContent(String contentId){
        System.out.println(contentId);
        return contentRepository.findAllById(Integer.parseInt(contentId));
    }

    public  void saveLike(String contentId){
        System.out.println(contentId);
        ContentLike contentLike=new ContentLike();
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Content content=contentRepository.findById(Integer.parseInt(contentId));
        contentLike.setTimestap(new Date());
        contentLike.setUser(user);
        contentLike.setContent(content);
        contentLikeRepository.save(contentLike);
    }

    public  void saveDislike(String contentId){
        System.out.println(contentId);
        ContentDislike contentDislike=new ContentDislike();
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Content content=contentRepository.findById(Integer.parseInt(contentId));
        contentDislike.setTimestap(new Date());
        contentDislike.setUser(user);
        contentDislike.setContent(content);
        contentDislikeRepository.save(contentDislike);
    }
}
