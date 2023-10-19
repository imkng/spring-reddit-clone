package com.programming.springredditclone.service;

import com.programming.springredditclone.dto.SubredditRequest;
import com.programming.springredditclone.model.Subreddit;
import com.programming.springredditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
    private final SubredditRepository subredditRepository;
    @Transactional
    public SubredditRequest save(SubredditRequest subredditRequest){
        Subreddit subredditSaved = subredditRepository.save(mapSubredditRequest(subredditRequest));
        subredditRequest.setId(subredditSaved.getId());
        return subredditRequest;
    }

    @Transactional(readOnly = true)
    public List<SubredditRequest> getAllSubreddits() {
        return subredditRepository.findAll()
                .stream().map(this::mapToSubredditRequest).collect(Collectors.toList());
    }

    private SubredditRequest mapToSubredditRequest(Subreddit subreddit) {
        return SubredditRequest.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .numberOfPost(subreddit.getPosts().size())
                .build();
    }

    private Subreddit mapSubredditRequest(SubredditRequest subredditRequest) {
        return  Subreddit.builder().name(subredditRequest.getName())
                .description(subredditRequest.getDescription())
                .build();
    }

}
