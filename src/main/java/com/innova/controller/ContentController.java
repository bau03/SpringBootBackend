package com.innova.controller;

import com.innova.dto.request.ContentForm;
import com.innova.dto.response.SuccessResponse;
import com.innova.model.Content;
import com.innova.service.CategoryServices;
import com.innova.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("api/content")
public class ContentController {
    @Autowired
    ContentService contentService;

    @Autowired
    CategoryServices categoryServices;

    @PostMapping("/register")
    public ResponseEntity<?> editUserRole(@Valid @RequestBody ContentForm contentForm) {
        contentService.save(contentForm);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK,
                "İçerik eklendi");
        return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
    }
    @GetMapping("/category")
    public ResponseEntity<?> getAllWriter() {
        return ResponseEntity.ok().body(categoryServices.getCategoryDetail());
    }
    @GetMapping("/contents")
    Page<Content> getContents(@PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        return contentService.getContentPage(pageable);
    }
    @GetMapping("/contents/{categoryName}")
    Page<Content> getCategoryContents(@PathVariable String categoryName,@PageableDefault(sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        return contentService.getCategoryContentPage(categoryName,pageable);
    }
}
