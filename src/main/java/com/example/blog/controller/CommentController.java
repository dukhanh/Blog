package com.example.blog.controller;

import com.example.blog.dto.request.CommentRequest;
import com.example.blog.dto.response.ApiResponse;
import com.example.blog.dto.response.CommentResponse;
import com.example.blog.dto.response.PagedResponse;
import com.example.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.blog.utils.AppConstants.DEFAULT_PAGE_NUMBER;
import static com.example.blog.utils.AppConstants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("api/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("{postId}")
    public ResponseEntity<PagedResponse<CommentResponse>> getAllComments(
            @PathVariable Long postId,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size
    ) {
        PagedResponse<CommentResponse> result = this.commentService.getAllByPost(postId, page, size);
        return ResponseEntity.ok(result);
    }

    @PostMapping("{postId}")
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long postId, @RequestBody CommentRequest creatComment) {
        CommentResponse result = this.commentService.addComment(postId, creatComment);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id, @RequestBody CommentRequest updateComment) {
        CommentResponse result = this.commentService.updateComment(id, updateComment);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long id) {
        ApiResponse result = this.commentService.deleteComment(id);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
