package com.example.blog.service.impl;

import com.example.blog.common.MessageContext;
import com.example.blog.dto.PostDTO;
import com.example.blog.dto.request.PostInformation;
import com.example.blog.dto.response.ApiResponse;
import com.example.blog.dto.response.PagedResponse;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import com.example.blog.utils.PermissionEdit;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PermissionEdit permissionEdit;

    public PostServiceImpl(PostRepository postRepository, UserService userService,
                           ModelMapper modelMapper, PermissionEdit permissionEdit) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.permissionEdit = permissionEdit;
    }

    @Override
    public Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(MessageContext.POST_NOT_FOUND)
        );
    }

    @Override
    public Post findPostByTitle(String title) {
        return postRepository.findByTitle(title).orElse(null);
    }

    @Override
    public PostDTO createPost(PostInformation createPost) {
        Post post = this.findPostByTitle(createPost.getTitle());
        if (post != null) {
            throw new IllegalArgumentException(MessageContext.CONFLICT_POST);
        } else {
            post = new Post();
        }
        User user = this.userService.getCurrentUser();
        post.setUser(user);
        post.setTitle(createPost.getTitle());
        post.setBody(createPost.getBody());
        post = this.postRepository.save(post);
        return this.modelMapper.map(post, PostDTO.class);
    }

    @Override
    public PostDTO getPost(Long postId) {
        Post post = this.findPostById(postId);
        return this.modelMapper.map(post, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(Long postId, PostInformation updatePost) {
        Post oldPost = this.findPostById(postId);
        if (permissionEdit.checkPermissionUpdateDelete(oldPost.getUser().getId())) {
            Post checkPost = this.findPostByTitle(updatePost.getTitle());
            if (checkPost != null) {
                throw new IllegalArgumentException(MessageContext.CONFLICT_POST);
            }
            oldPost.setBody(updatePost.getBody());
            oldPost.setTitle(updatePost.getTitle());
            oldPost = this.postRepository.save(oldPost);
            return this.modelMapper.map(oldPost, PostDTO.class);
        } else {
            throw new AccessDeniedException(MessageContext.ACCESS_DENIED);
        }
    }

    @Override
    public ApiResponse deletePost(Long postId) {
        Post post = this.findPostById(postId);
        if (permissionEdit.checkPermissionUpdateDelete(post.getUser().getId())) {
            this.postRepository.delete(post);
            return new ApiResponse(new Date(), HttpStatus.OK, MessageContext.DELETE_POST_SUCCESS);
        }
        return new ApiResponse(new Date(), HttpStatus.FORBIDDEN, MessageContext.ACCESS_DENIED);
    }

    @Override
    public PagedResponse<PostDTO> getAllPosts(String titleSearch, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createAt");
        Page<Post> posts;
        if (titleSearch == null) {
            posts = postRepository.findAll(pageable);
        } else {
            posts = postRepository.findByTitleContains(titleSearch, pageable);
        }
        List<Post> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();
        List<PostDTO> result = content.stream().map(post -> this.modelMapper.map(post, PostDTO.class)).toList();
        return new PagedResponse<>(result, posts.getNumber(), posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isFirst(), posts.isLast());
    }

}
