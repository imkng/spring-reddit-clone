package com.programming.springredditclone.service;

import com.programming.springredditclone.dto.VoteDto;
import com.programming.springredditclone.exception.PostNotFoundException;
import com.programming.springredditclone.exception.SpringRedditException;
import com.programming.springredditclone.model.Post;
import com.programming.springredditclone.model.Vote;
import com.programming.springredditclone.repository.PostRepository;
import com.programming.springredditclone.repository.UserRepository;
import com.programming.springredditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.programming.springredditclone.model.VoteType.UPVOTE;


@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Transactional
    public void saveVote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + voteDto.getPostId()));

        User loggedUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        com.programming.springredditclone.model.User currentUser = userRepository.findByUserName(loggedUser.getUsername())
                .orElseThrow(() -> new SpringRedditException("User not found with username: " + loggedUser.getUsername()));

        Optional<Vote> voteByPostAndUser = voteRepository.findByPostAndUserOrderByVoteIdDesc(post, currentUser);

        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post, currentUser));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post, com.programming.springredditclone.model.User user) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(user)
                .build();
    }
}
