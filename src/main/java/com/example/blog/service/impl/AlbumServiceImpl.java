package com.example.blog.service.impl;

import com.example.blog.common.MessageContext;
import com.example.blog.dto.request.AlbumInfor;
import com.example.blog.dto.response.AlbumResponse;
import com.example.blog.dto.response.ApiResponse;
import com.example.blog.entity.Album;
import com.example.blog.entity.User;
import com.example.blog.entity.role.RoleName;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.AlbumRepository;
import com.example.blog.service.AlbumService;
import com.example.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;

    public AlbumServiceImpl(AlbumRepository albumRepository, UserService userService, ModelMapper modelMapper) {
        this.albumRepository = albumRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
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
    public AlbumResponse createAlbum(AlbumInfor createAlbum) {
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
    public AlbumResponse updateAlbum(Long id, AlbumInfor updateAlbum) {
        Album oldAlbum = this.findAlbumById(id);
        Album checkAlbum = this.findAlbumByTitle(updateAlbum.getTitle());
        if (checkAlbum != null) {
            throw new IllegalArgumentException(MessageContext.CONFLICT_ALBUM);
        }
        oldAlbum.setTitle(updateAlbum.getTitle());
        oldAlbum = this.albumRepository.save(oldAlbum);
        return this.modelMapper.map(oldAlbum, AlbumResponse.class);
    }

    @Override
    public ApiResponse deleteAlbum(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername((String) authentication.getPrincipal());
        Album album = this.findAlbumById(id);
        if (album.getUser().getId().equals(user.getId()) || authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            this.albumRepository.delete(album);
            return new ApiResponse(new Date(), HttpStatus.OK, MessageContext.DELETE_ALBUM_SUCCESS);
        }
        return new ApiResponse(new Date(), HttpStatus.FORBIDDEN, MessageContext.ACCESS_DENIED);
    }


}
