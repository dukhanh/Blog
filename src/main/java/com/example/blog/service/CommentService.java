package com.example.blog.service;

import com.example.blog.dto.request.CommentRequest;
import com.example.blog.dto.response.ApiResponse;
import com.example.blog.dto.response.CommentResponse;
import com.example.blog.dto.response.PagedResponse;
import com.example.blog.entity.Comment;

public interface CommentService {

    Comment findById(Long id);
    CommentResponse addComment(Long postId, CommentRequest creatComment);

    CommentResponse updateComment(Long id, CommentRequest updateComment);

    ApiResponse deleteComment(Long id);
    PagedResponse<CommentResponse> getAllByPost(Long postId, int page, int size);
}
