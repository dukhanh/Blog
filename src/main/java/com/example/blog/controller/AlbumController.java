package com.example.blog.controller;

import com.example.blog.dto.request.AlbumInformation;
import com.example.blog.dto.response.AlbumResponse;
import com.example.blog.dto.response.ApiResponse;
import com.example.blog.service.AlbumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/album")
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllAlbums() {
        return null;
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
        ApiResponse result =  this.albumService.deleteAlbum(id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getAlbum(@PathVariable Long id) {
        return null;
    }

}
