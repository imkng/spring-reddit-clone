package com.programming.springredditclone.controller;

import com.programming.springredditclone.dto.VoteDto;
import com.programming.springredditclone.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> saveVote(@RequestBody VoteDto voteDto) {
        voteService.saveVote(voteDto);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

}
