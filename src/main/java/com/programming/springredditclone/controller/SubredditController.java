package com.programming.springredditclone.controller;

import com.programming.springredditclone.dto.SubredditRequest;
import com.programming.springredditclone.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;
    @PostMapping()
    public ResponseEntity<SubredditRequest> createSubreddit(@RequestBody SubredditRequest subredditRequest){
        return new ResponseEntity<SubredditRequest> (subredditService.save(subredditRequest), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<SubredditRequest>> getAllSubreddits(){
        return new ResponseEntity< List<SubredditRequest> >(subredditService.getAllSubreddits(), HttpStatus.OK);
    }
}
