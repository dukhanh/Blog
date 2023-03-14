package com.example.blog.controller;

import com.example.blog.service.PhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/photo")
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("{albumId}")
    public ResponseEntity<?> addPhoto(@PathVariable Long albumId) {
        return null;
    }

    @PutMapping("{photoId}")
    public ResponseEntity<?> updatePhoto(@PathVariable Long photoId) {
        return null;
    }

    @DeleteMapping("{photoId}")
    public ResponseEntity<?> deletePhoto(@PathVariable Long photoId) {
        return null;
    }

    @GetMapping("{photoId}")
    public ResponseEntity<?> getPhoto(@PathVariable Long photoId) {
        return null;
    }
}
