package com.programming.springredditclone.service;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.programming.springredditclone.dto.PostRequest;
import com.programming.springredditclone.dto.PostResponse;
import com.programming.springredditclone.exception.SpringRedditException;
import com.programming.springredditclone.exception.SpringRedditNotFoundException;
import com.programming.springredditclone.exception.UserNotFoundException;
import com.programming.springredditclone.model.Post;
import com.programming.springredditclone.model.Subreddit;
import com.programming.springredditclone.repository.CommentRepository;
import com.programming.springredditclone.repository.PostRepository;
import com.programming.springredditclone.repository.SubredditRepository;
import com.programming.springredditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final SubredditRepository subredditRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public void savePost(PostRequest postRequest) {
        Subreddit subreddit = (Subreddit) subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(() ->
                new SpringRedditException("subreddit not found with the given name: " + postRequest.getSubredditName()));
        User loggedUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        com.programming.springredditclone.model.User currentUser = userRepository.findByUserName(loggedUser.getUsername())
                .orElseThrow(() -> new SpringRedditException("User not found with username: " + loggedUser.getUsername()));
        Post post = mapToPost(postRequest, subreddit, currentUser);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new SpringRedditException("Post not exist with id: " + id));
        return mapPostToPostResponse(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(this::mapPostToPostResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubredditId(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(
                () -> new SpringRedditNotFoundException("Subreddit not found with the id: " + id));
        List<Post> postList = postRepository.findAllBySubreddit(subreddit);
        return postList.stream().map(this::mapPostToPostResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        com.programming.springredditclone.model.User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with the given username: " + username));
        return postRepository.findByUser(user).stream().map(this::mapPostToPostResponse).collect(Collectors.toList());
    }

    private PostResponse mapPostToPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getPostId())
                .url(post.getUrl())
                .postName(post.getPostName())
                .description(post.getDescription())
                .subredditName(post.getSubreddit().getName())
                .userName(post.getUser().getUserName())
                .commentCount(commentCount(post))
                .duration(getDurationPost(post))
                .voteCount(post.getVoteCount())
                .build();
    }

    private String getDurationPost(Post post) {
        return TimeAgo.using(post.getCreatedAt().toEpochMilli());
    }

    private Integer commentCount(Post post) {
        return commentRepository.findAllByPost(post).size();
    }

    private Post mapToPost(PostRequest postRequest, Subreddit subreddit, com.programming.springredditclone.model.User currentUser) {
        return Post.builder()
                .postName(postRequest.getPostName())
                .url(postRequest.getUrl())
                .description(postRequest.getDescription())
                .createdAt(Instant.now())
                .subreddit(subreddit)
                .user(currentUser)
                .voteCount(0)
                .build();
    }
}
