package com.example.blog.controller;

import com.example.blog.dto.request.AlbumInformation;
import com.example.blog.dto.response.AlbumResponse;
import com.example.blog.dto.response.ApiResponse;
import com.example.blog.dto.response.PagedResponse;
import com.example.blog.dto.response.PhotoResponse;
import com.example.blog.service.AlbumService;
import com.example.blog.service.PhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.blog.utils.AppConstants.DEFAULT_PAGE_NUMBER;
import static com.example.blog.utils.AppConstants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("api/album")
public class AlbumController {
    private final AlbumService albumService;
    private final PhotoService photoService;

    public AlbumController(AlbumService albumService, PhotoService photoService) {
        this.albumService = albumService;
        this.photoService = photoService;
    }

    @GetMapping("")
    public ResponseEntity<PagedResponse<AlbumResponse>> getAllAlbums(
            @RequestParam(value = "titleSearch", required = false) String titleSearch,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size
    ) {
        PagedResponse<AlbumResponse> result = this.albumService.getAllAlbums(titleSearch, page, size);
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<AlbumResponse> createAlbum(@RequestBody AlbumInformation createAlbum) {
        AlbumResponse result = this.albumService.createAlbum(createAlbum);
        return ResponseEntity.ok(result);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateAlbum(@PathVariable Long id, @RequestBody AlbumInformation updateAlbum) {
        AlbumResponse result = this.albumService.updateAlbum(id, updateAlbum);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteAlbum(@PathVariable Long id) {
        ApiResponse result = this.albumService.deleteAlbum(id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("{id}/photos")
    public ResponseEntity<PagedResponse<PhotoResponse>> getAllPhotosByAlbum(
            @PathVariable Long id,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size) {
        PagedResponse<PhotoResponse> result = this.photoService.getAllPhotoByAlbum(id, page, size);
        return ResponseEntity.ok(result);
    }

}
