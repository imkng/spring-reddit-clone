package com.programming.springredditclone.controller;

import com.programming.springredditclone.dto.PostRequest;
import com.programming.springredditclone.dto.PostResponse;
import com.programming.springredditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity savePost(@RequestBody PostRequest postRequest){
        postService.savePost(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return new ResponseEntity<List<PostResponse>>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<PostResponse> getPostById(@PathVariable Long id){
        return new ResponseEntity<PostResponse>(postService.getPostById(id), HttpStatus.OK);
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubredditId(@PathVariable Long id){
            return new ResponseEntity<>(postService.getPostsBySubredditId(id), HttpStatus.OK);
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username){
        return new ResponseEntity<>(postService.getPostsByUsername(username), HttpStatus.OK);
    }
}
