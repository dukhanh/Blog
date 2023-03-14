package com.example.blog.service.impl;

import com.example.blog.common.MessageContext;
import com.example.blog.dto.request.AlbumInformation;
import com.example.blog.dto.response.AlbumResponse;
import com.example.blog.dto.response.ApiResponse;
import com.example.blog.entity.Album;
import com.example.blog.entity.User;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.AlbumRepository;
import com.example.blog.service.AlbumService;
import com.example.blog.service.UserService;
import com.example.blog.utils.PermissionEdit;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PermissionEdit permissionEdit;


    public AlbumServiceImpl(AlbumRepository albumRepository, UserService userService,
                            ModelMapper modelMapper, PermissionEdit permissionEdit) {
        this.albumRepository = albumRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.permissionEdit = permissionEdit;
    }

    @Override
    public Album findAlbumByTitle(String title) {
        return albumRepository.findByTitle(title).orElse(null);
    }

    @Override
    public Album findAlbumById(Long id) {
        return albumRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(MessageContext.ALBUM_NOT_FOUND)
        );
    }

    @Override
    public AlbumResponse createAlbum(AlbumInformation createAlbum) {
        Album album = this.findAlbumByTitle(createAlbum.getTitle());
        if (album != null) {
            throw new IllegalArgumentException(MessageContext.CONFLICT_ALBUM);
        } else {
            album = new Album();
        }
        User user = this.userService.getCurrentUser();
        album.setTitle(createAlbum.getTitle());
        album.setUser(user);
        album = this.albumRepository.save(album);
        return this.modelMapper.map(album, AlbumResponse.class);
    }

    @Override
    public AlbumResponse updateAlbum(Long id, AlbumInformation updateAlbum) {
        Album oldAlbum = this.findAlbumById(id);
        if (permissionEdit.checkPermissionUpdateDelete(oldAlbum.getUser().getId())) {
            Album checkAlbum = this.findAlbumByTitle(updateAlbum.getTitle());
            if (checkAlbum != null) {
                throw new IllegalArgumentException(MessageContext.CONFLICT_ALBUM);
            }
            oldAlbum.setTitle(updateAlbum.getTitle());
            oldAlbum = this.albumRepository.save(oldAlbum);
            return this.modelMapper.map(oldAlbum, AlbumResponse.class);
        } else {
            throw new AccessDeniedException(MessageContext.ACCESS_DENIED);
        }
    }

    @Override
    public ApiResponse deleteAlbum(Long id) {
        Album album = this.findAlbumById(id);
        if (permissionEdit.checkPermissionUpdateDelete(album.getUser().getId())) {
            this.albumRepository.delete(album);
            return new ApiResponse(new Date(), HttpStatus.OK, MessageContext.DELETE_ALBUM_SUCCESS);
        }
        return new ApiResponse(new Date(), HttpStatus.FORBIDDEN, MessageContext.ACCESS_DENIED);
    }


}
