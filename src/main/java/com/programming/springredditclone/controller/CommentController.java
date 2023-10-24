package com.programming.springredditclone.controller;

import com.programming.springredditclone.dto.CommentRequest;
import com.programming.springredditclone.dto.CommentResponse;
import com.programming.springredditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComments(@RequestBody CommentRequest commentRequest) {
        commentService.createComment(commentRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-postId/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsForPost(@PathVariable Long postId) {
        return new ResponseEntity<List<CommentResponse>>(commentService.getAllCommentsForPost(postId), HttpStatus.OK);
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsForUser(@PathVariable String username) {
        return new ResponseEntity<List<CommentResponse>>(commentService.getAllCommentsForUser(username), HttpStatus.OK);
    }

}
