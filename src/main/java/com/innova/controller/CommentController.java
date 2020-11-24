package com.innova.controller;

import com.innova.dto.request.CommentForm;
import com.innova.dto.request.ContentLikeForm;
import com.innova.dto.response.SuccessResponse;
import com.innova.model.Comment;
import com.innova.service.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import javax.validation.Valid;

@RestController
@RequestMapping("api/comment")
public class CommentController {

    @Autowired
    CommentServices commentServices;

    @PostMapping("/commentregister")
    public ResponseEntity<?> editUserRole(@Valid @RequestBody CommentForm commentForm) {
        commentServices.save(commentForm);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "Successfully comment.");
        return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
    }
    @GetMapping("/commentget/{contentId}")
    public ResponseEntity<Page<Comment>> getComment(@PathVariable String contentId, @PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.ok().body(commentServices.getComment(contentId,pageable));
    }

    @PutMapping("/like/{commentId}")
    public ResponseEntity<?> editLike(@Valid @PathVariable String commentId) {
        ContentLikeForm contentLikeForm=new ContentLikeForm();
        contentLikeForm.setId(commentId);
        commentServices.like(contentLikeForm);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK,
                "Like");
        return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
    }

    @PutMapping("/dislike/{contentId}")
    public ResponseEntity<?> editDislike(@Valid @RequestBody @PathVariable String contentId) {
        ContentLikeForm contentLikeForm=new ContentLikeForm();
        contentLikeForm.setId(contentId);
        commentServices.dislike(contentLikeForm);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK,
                "Dislike");
        return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
    }
}
