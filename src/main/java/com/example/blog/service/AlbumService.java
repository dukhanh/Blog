package com.example.blog.service;

import com.example.blog.dto.request.AlbumInfor;
import com.example.blog.dto.response.AlbumResponse;
import com.example.blog.dto.response.ApiResponse;
import com.example.blog.entity.Album;

public interface
AlbumService {
    AlbumResponse createAlbum(AlbumInfor createAlbum);

    Album findAlbumByTitle(String  title);

    Album findAlbumById(Long id);

    AlbumResponse updateAlbum(Long id, AlbumInfor updateAlbum);

    ApiResponse deleteAlbum(Long id);
}
