package com.example.blog.service;

import com.example.blog.dto.PostDTO;
import com.example.blog.dto.request.PostInformation;
import com.example.blog.dto.response.ApiResponse;
import com.example.blog.dto.response.PagedResponse;
import com.example.blog.entity.Post;

public interface PostService {
    Post findPostById(Long id);
    Post findPostByTitle(String title);
    PostDTO createPost(PostInformation createPost);
    PostDTO getPost(Long postId);
    PostDTO updatePost(Long postId, PostInformation updatePost);
    ApiResponse deletePost(Long postId);
    PagedResponse<PostDTO> getAllPosts(String titleSearch, int page, int size);
}
