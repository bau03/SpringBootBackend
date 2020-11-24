package com.innova.service;

import com.innova.constants.ErrorCodes;
import com.innova.dto.request.CommentForm;
import com.innova.dto.request.ContentLikeForm;
import com.innova.exception.BadRequestException;
import com.innova.model.Comment;
import com.innova.model.Content;
import com.innova.model.User;
import com.innova.repository.CommentRepository;
import com.innova.repository.ContentRepository;
import com.innova.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.Date;
import java.util.Set;

@Service
public class CommentServices {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    UserRepository userRepository;

    public void save(CommentForm commentForm){
        Comment comment= new Comment(commentForm.getComment());
        comment.setComment_like_number(0);
        comment.setComment_dislike_number(0);
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Content content=contentRepository.findById(Integer.parseInt(commentForm.getContent_id()));
        content.setComment_number(content.getComment_number()+1);
        contentRepository.save(content);
        comment.setContent(content);
        comment.setUser(user);
        comment.setTimestap(new Date());
        commentRepository.save(comment);
    }

    public Page<Comment> getComment(String contentId, Pageable pageable){
        Content content=contentRepository.findById(Integer.parseInt(contentId));
        return commentRepository.findByContent(content,pageable);
    }

    public void like(ContentLikeForm contentLikeForm){
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Comment comment = commentRepository.findById(Integer.parseInt(contentLikeForm.getId()));

        if (commentRepository.existsById(Integer.parseInt(contentLikeForm.getId()))) {
            if(!user.getCommentLike().contains(comment)) {
                comment.setComment_like_number(comment.getComment_like_number()+1);
                Set<User> userLike =comment.getUserLike();
                userLike.add(user);
                comment.setUserLike(userLike);
                commentRepository.save(comment);
                Set<Comment> commentLike = user.getCommentLike();
                commentLike.add(comment);
                user.setCommentLike(commentLike);
                userRepository.save(user);
            }
            else {
                comment.setComment_like_number(comment.getComment_like_number()-1);
                Set<User> userLike=comment.getUserLike();
                userLike.remove(user);
                comment.setUserLike(userLike);
                commentRepository.save(comment);
                Set<Comment> commentLike = user.getCommentLike();
                commentLike.remove(comment);
                user.setCommentLike(commentLike);
                userRepository.save(user);
            }

            }
        else {
            throw new BadRequestException("Something is wrong", ErrorCodes.SOMETHING_IS_WRONG);
        }
        }

    public void dislike(ContentLikeForm contentLikeForm){
        User user = userServiceImpl.getUserWithAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Comment comment = commentRepository.findById(Integer.parseInt(contentLikeForm.getId()));
        if (commentRepository.existsById(Integer.parseInt(contentLikeForm.getId()))) {

            if (!user.getCommentDislike().contains(comment)) {
                comment.setComment_dislike_number(comment.getComment_dislike_number()+1);
                Set<User> userDislike = comment.getUserDislike();
                userDislike.add(user);
                comment.setUserDislike(userDislike);
                commentRepository.save(comment);
                Set<Comment> commentDislike=user.getCommentDislike();
                commentDislike.add(comment);
                user.setCommentDislike(commentDislike);
                userRepository.save(user);
            }
            else {
                comment.setComment_dislike_number(comment.getComment_dislike_number()-1);
                Set<User> userDislike=comment.getUserDislike();
                userDislike.remove(user);
                comment.setUserDislike(userDislike);
                commentRepository.save(comment);
                Set<Comment> commentDislike=user.getCommentDislike();
                commentDislike.remove(comment);
                user.setCommentDislike(commentDislike);
                userRepository.save(user);
            }
        }
        else {
            throw new BadRequestException("Something is wrong", ErrorCodes.SOMETHING_IS_WRONG);
        }
    }
}
