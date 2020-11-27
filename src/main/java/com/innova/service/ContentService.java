package com.innova.service;

import com.innova.constants.ErrorCodes;
import com.innova.dto.request.ContentForm;
import com.innova.dto.request.ContentLikeForm;
import com.innova.dto.response.SuccessResponse;
import com.innova.exception.BadRequestException;
import com.innova.model.*;
import com.innova.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
        Content content=new Content(contentForm.getContent(),contentForm.getContentHeader());
        content.setContentLikeNumber(0);
        content.setContent_dislike_number(0);
        content.setComment_number(0);
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        content.setUser(user);
        Category category= categoryServices.getCategory(contentForm.getCategoryId());
        content.setCategory(category);
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
        return contentRepository.findAllById(Integer.parseInt(contentId));
    }

    public void like(ContentLikeForm contentLikeForm){
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Content content = contentRepository.findById(Integer.parseInt(contentLikeForm.getId()));
        if (contentRepository.existsById(Integer.parseInt(contentLikeForm.getId()))) {
            if (!user.getContentLike().contains(content)) {
                content.setContentLikeNumber(content.getContentLikeNumber()+1);
                Set<User> userLike = content.getUserLike();
                userLike.add(user);
                content.setUserLike(userLike);
                contentRepository.save(content);
                Set<Content> contentLike = user.getContentLike();
                contentLike.add(content);
                user.setContentLike(contentLike);
                userRepository.save(user);
            }
            else {
                content.setContentLikeNumber(content.getContentLikeNumber()-1);
                Set<User> userLike = content.getUserLike();
                userLike.remove(user);
                content.setUserLike(userLike);
                contentRepository.save(content);
                Set<Content> contentLike = user.getContentLike();
                contentLike.remove(content);
                user.setContentLike(contentLike);
                userRepository.save(user);
            }
        }
        else {
            throw new BadRequestException("Something is wrong", ErrorCodes.SOMETHING_IS_WRONG);
        }
    }

    public void dislike(ContentLikeForm contentLikeForm){
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        if (contentRepository.existsById(Integer.parseInt(contentLikeForm.getId()))) {
            Content content = contentRepository.findById(Integer.parseInt(contentLikeForm.getId()));
            if (!user.getContentDislike().contains(content)) {
                content.setContent_dislike_number(content.getContent_dislike_number()+1);
                Set<User> userDislike = content.getUserDislike();
                userDislike.add(user);
                content.setUserDislike(userDislike);
                contentRepository.save(content);
                Set<Content> contentDislike = user.getContentDislike();
                contentDislike.add(content);
                user.setContentDislike(contentDislike);
                userRepository.save(user);
            }
            else {
                content.setContent_dislike_number(content.getContent_dislike_number()-1);
                Set<User> userDislike = content.getUserDislike();
                userDislike.remove(user);
                content.setUserDislike(userDislike);
                contentRepository.save(content);
                Set<Content> contentDislike = user.getContentDislike();
                contentDislike.remove(content);
                user.setContentDislike(contentDislike);
                userRepository.save(user);
            }
        }
        else {
            throw new BadRequestException("Something is wrong", ErrorCodes.SOMETHING_IS_WRONG);
        }
    }
    public Page<Content> getSearch(String contentHeader,Pageable pageable){
        Page<Content> content=contentRepository.findAll(contentHeader,pageable);
        if(content.getContent().size()!=0){
            return contentRepository.findAll(contentHeader,pageable);
        }
        else{
            throw new BadRequestException("Something is wrong", ErrorCodes.SOMETHING_IS_WRONG);
        }

    }

    public Page<Content> getTrendContent(Pageable pageable){

        return contentRepository.findByOrderByContentLikeNumberDesc(pageable);
    }
}
