package com.example.blog.controller;

import com.example.blog.dto.request.PhotoInformation;
import com.example.blog.dto.response.ApiResponse;
import com.example.blog.dto.response.PagedResponse;
import com.example.blog.dto.response.PhotoResponse;
import com.example.blog.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.blog.utils.AppConstants.DEFAULT_PAGE_NUMBER;
import static com.example.blog.utils.AppConstants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("api/photo")
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("{albumId}")
    public ResponseEntity<PhotoResponse> addPhoto(@PathVariable Long albumId, @RequestBody PhotoInformation addPhoto) {
        PhotoResponse result = this.photoService.addPhoto(albumId, addPhoto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("{photoId}")
    public ResponseEntity<PhotoResponse> updatePhoto(@PathVariable Long photoId, @RequestBody PhotoInformation updatePhoto) {
        PhotoResponse result = this.photoService.updatePhoto(photoId, updatePhoto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{photoId}")
    public ResponseEntity<ApiResponse> deletePhoto(@PathVariable Long photoId) {
        ApiResponse result = this.photoService.deletePhoto(photoId);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("{photoId}")
    public ResponseEntity<PhotoResponse> getPhoto(@PathVariable Long photoId) {
        PhotoResponse result = this.photoService.getPhoto(photoId);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public PagedResponse<PhotoResponse> getAllPhotos(
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size) {
        return photoService.getAllPhotos(page, size);
    }
}
