package com.example.blog.service.impl;

import com.example.blog.common.MessageContext;
import com.example.blog.dto.request.CommentRequest;
import com.example.blog.dto.response.ApiResponse;
import com.example.blog.dto.response.CommentResponse;
import com.example.blog.dto.response.PagedResponse;
import com.example.blog.entity.Comment;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.CommentRepository;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
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
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PermissionEdit permissionEdit;


    public CommentServiceImpl(CommentRepository commentRepository, PostService postService,
                              UserService userService, ModelMapper modelMapper, PermissionEdit permissionEdit) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.permissionEdit = permissionEdit;
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(MessageContext.COMMENT_NOT_FOUND)
        );
    }

    @Override
    public CommentResponse addComment(Long postId, CommentRequest creatComment) {
        Post post = this.postService.findPostById(postId);
        User user = this.userService.getCurrentUser();
        Comment comment = new Comment();
        comment.setContent(creatComment.getContent());
        comment.setPost(post);
        comment.setUser(user);
        comment = this.commentRepository.save(comment);
        return this.modelMapper.map(comment, CommentResponse.class);
    }

    @Override
    public CommentResponse updateComment(Long id, CommentRequest updateComment) {
        Comment comment = this.findById(id);
        if(permissionEdit.checkPermissionUpdateDelete(comment.getUser().getId())){
            comment.setContent(updateComment.getContent());
            comment = this.commentRepository.save(comment);
            return this.modelMapper.map(comment, CommentResponse.class);
        }else{
            throw new AccessDeniedException(MessageContext.ACCESS_DENIED);
        }

    }

    @Override
    public ApiResponse deleteComment(Long id) {
        Comment comment = this.findById(id);
        if(permissionEdit.checkPermissionUpdateDelete(comment.getUser().getId())){
            commentRepository.delete(comment);
            return new ApiResponse(new Date(), HttpStatus.OK,MessageContext.DELETE_COMMENT_SUCCESS);
        }
        return new ApiResponse(new Date(), HttpStatus.FORBIDDEN, MessageContext.ACCESS_DENIED);
    }

    @Override
    public PagedResponse<CommentResponse> getAllByPost(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createAt");
        Page<Comment> comments= this.commentRepository.findAll(postId, pageable);
        List<Comment> content = comments.getNumberOfElements()==0? Collections.emptyList():comments.getContent();
        List<CommentResponse> result = content.stream().map(comment -> this.modelMapper.map(comment, CommentResponse.class)).toList();
        return new PagedResponse<>(result,
                comments.getNumber(),
                comments.getSize(),
                comments.getTotalElements(),
                comments.getTotalPages(),
                comments.isFirst(),
                comments.isLast());
    }
}
