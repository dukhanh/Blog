package com.example.blog.service.impl;

import com.example.blog.repository.PhotoRepository;
import com.example.blog.service.PhotoService;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService{
    private final PhotoRepository photoRepository;

    public PhotoServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }
}
