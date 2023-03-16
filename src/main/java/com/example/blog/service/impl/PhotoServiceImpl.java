package com.example.blog.service.impl;

import com.example.blog.common.MessageContext;
import com.example.blog.dto.request.PhotoInformation;
import com.example.blog.dto.response.ApiResponse;
import com.example.blog.dto.response.PagedResponse;
import com.example.blog.dto.response.PhotoResponse;
import com.example.blog.entity.Album;
import com.example.blog.entity.Comment;
import com.example.blog.entity.Photo;
import com.example.blog.entity.User;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.PhotoRepository;
import com.example.blog.service.AlbumService;
import com.example.blog.service.PhotoService;
import com.example.blog.service.UserService;
import com.example.blog.utils.PermissionEdit;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PermissionEdit permissionEdit;
    private final AlbumService albumService;

    public PhotoServiceImpl(PhotoRepository photoRepository, UserService userService,
                            ModelMapper modelMapper, PermissionEdit permissionEdit, AlbumService albumService) {
        this.photoRepository = photoRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.permissionEdit = permissionEdit;
        this.albumService = albumService;
    }


    @Override
    public Photo findPhotoByTitle(String title) {
        return this.photoRepository.findByTitle(title).orElse(null);
    }

    @Override
    public Photo findPhotoById(Long id) {
        return photoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(MessageContext.PHOTO_NOT_FOUND)
        );
    }

    @Override
    public PhotoResponse addPhoto(Long albumId, PhotoInformation addPhoto) {
        Album album = this.albumService.findAlbumById(albumId);
        User user = this.userService.getCurrentUser();
        if (album.getUser().getId().equals(user.getId())) {
            Photo photo = new Photo();
            photo.setTitle(addPhoto.getTitle());
            photo.setUrl(addPhoto.getUrl());
            photo.setAlbum(album);
            photo = this.photoRepository.save(photo);
            return this.modelMapper.map(photo, PhotoResponse.class);
        }
        throw new AccessDeniedException(MessageContext.ACCESS_DENIED);
    }

    @Override
    public PhotoResponse updatePhoto(Long photoId, PhotoInformation updatePhoto) {
        Photo photo = this.findPhotoById(photoId);
        if (permissionEdit.checkPermissionUpdateDelete(photo.getAlbum().getUser().getId())) {
            photo.setTitle(updatePhoto.getTitle());
            photo.setUrl(updatePhoto.getUrl());
            photo = photoRepository.save(photo);
            return this.modelMapper.map(photo, PhotoResponse.class);
        }
        throw new AccessDeniedException(MessageContext.ACCESS_DENIED);
    }

    @Override
    public ApiResponse deletePhoto(Long photoId) {
        Photo photo = this.findPhotoById(photoId);
        if (permissionEdit.checkPermissionUpdateDelete(photo.getAlbum().getUser().getId())) {
            photoRepository.delete(photo);
            return new ApiResponse(new Date(), HttpStatus.OK, MessageContext.DELETE_PHOTO_SUCCESS);
        }
        return new ApiResponse(new Date(), HttpStatus.FORBIDDEN, MessageContext.ACCESS_DENIED);
    }

    @Override
    public PhotoResponse getPhoto(Long photoId) {
        Photo photo = this.findPhotoById(photoId);
        return this.modelMapper.map(photo, PhotoResponse.class);
    }

    public PagedResponse<PhotoResponse> getAllPhotos(int page,int size){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createAt");
        Page<Photo> photos = photoRepository.findAll(pageable);
        return this.pagedPhoto(photos);
    }

    @Override
    public PagedResponse<PhotoResponse> getAllPhotoByAlbum(Long albumId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createAt");
        Page<Photo> photos = photoRepository.findAllByAlbum(albumId, pageable);
        return this.pagedPhoto(photos);
    }

    public PagedResponse<PhotoResponse> pagedPhoto( Page<Photo> photos ){
        List<Photo> content = photos.getNumberOfElements() == 0 ? Collections.emptyList() : photos.getContent();
        List<PhotoResponse> result = content.stream().map(photo -> this.modelMapper.map(photo, PhotoResponse.class)).toList();
        return new PagedResponse<>(result,
                photos.getNumber(),
                photos.getSize(),
                photos.getTotalElements(),
                photos.getTotalPages(),
                photos.isFirst(),
                photos.isLast());
    }
}
