package com.example.blog.controller;

import com.example.blog.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("{postId}")
    public ResponseEntity<?> getAllComments(@PathVariable Long postId){
        return null;
    }

    @PostMapping("{postId}")
    public ResponseEntity<?> addComment(@PathVariable Long postId){
        return null;
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateComment( @PathVariable Long id){
        return null;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        return null;
    }
}
