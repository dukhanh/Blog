package com.example.blog.controller;

import com.example.blog.dto.PostDTO;
import com.example.blog.dto.request.PostInformation;
import com.example.blog.dto.response.ApiResponse;
import com.example.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllPosts(){
return null;
    }
    @PostMapping("")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostInformation createPost){
        PostDTO result = this.postService.createPost(createPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @GetMapping("{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long postId){
        PostDTO result = this.postService.getPost(postId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("{postId}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long postId,@RequestBody PostInformation updatePost){
        PostDTO result = this.postService.updatePost(postId, updatePost);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId){
        ApiResponse result = this.postService.deletePost(postId);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
