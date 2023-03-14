package com.example.blog.service;

import com.example.blog.dto.request.AlbumInformation;
import com.example.blog.dto.response.AlbumResponse;
import com.example.blog.dto.response.ApiResponse;
import com.example.blog.entity.Album;

public interface
AlbumService {
    AlbumResponse createAlbum(AlbumInformation createAlbum);

    Album findAlbumByTitle(String  title);

    Album findAlbumById(Long id);

    AlbumResponse updateAlbum(Long id, AlbumInformation updateAlbum);

    ApiResponse deleteAlbum(Long id);
}
