package com.example.blog.service;

import com.example.blog.dto.request.PhotoInformation;
import com.example.blog.dto.response.ApiResponse;
import com.example.blog.dto.response.PagedResponse;
import com.example.blog.dto.response.PhotoResponse;
import com.example.blog.entity.Photo;

public interface PhotoService {

    Photo findPhotoByTitle(String title);
    Photo findPhotoById(Long id);
    PhotoResponse addPhoto(Long albumId, PhotoInformation addPhoto);

    PhotoResponse updatePhoto(Long photoId, PhotoInformation updatePhoto);

    ApiResponse deletePhoto(Long photoId);

    PhotoResponse getPhoto(Long photoId);
    PagedResponse<PhotoResponse> getAllPhotos(int page,int size);
    PagedResponse<PhotoResponse> getAllPhotoByAlbum(Long albumId, int page, int size);
}
