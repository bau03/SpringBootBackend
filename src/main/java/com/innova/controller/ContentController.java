package com.innova.controller;

import com.innova.dto.request.ContentForm;
import com.innova.dto.response.SuccessResponse;
import com.innova.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/content")
public class ContentController {
    @Autowired
    ContentService contentService;

    @PostMapping("/register")
    public ResponseEntity<?> editUserRole(@Valid @RequestBody ContentForm contentForm) {
        contentService.save(contentForm);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK,
                "İçerik eklendi");
        return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
    }
    @GetMapping("/get")
    public ResponseEntity<?> getAllWriter() {
        return ResponseEntity.ok().body(contentService.getContent());
    }
}
