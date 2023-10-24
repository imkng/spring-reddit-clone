package com.programming.springredditclone.service;

import com.programming.springredditclone.dto.CommentRequest;
import com.programming.springredditclone.dto.CommentResponse;
import com.programming.springredditclone.exception.PostNotFoundException;
import com.programming.springredditclone.exception.UserIsNotLoggedIn;
import com.programming.springredditclone.model.Comment;
import com.programming.springredditclone.model.NotificationEmail;
import com.programming.springredditclone.model.Post;
import com.programming.springredditclone.model.User;
import com.programming.springredditclone.repository.CommentRepository;
import com.programming.springredditclone.repository.PostRepository;
import com.programming.springredditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    @Transactional
    public void createComment(CommentRequest commentRequest) {
        org.springframework.security.core.userdetails.User loggedUser = authService.getCurrentUser().orElseThrow(() -> new UserIsNotLoggedIn("Please login!!"));
        User user = userRepository.findByUserName(loggedUser.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("User not found with the username: " + commentRequest.getUsername()));
        Post post = postRepository.findById(commentRequest.getPostId()).orElseThrow(() -> new PostNotFoundException("Post not found!!"));
        Comment comment = mapCommentRequestToComment(commentRequest, post, user);
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUserName() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());

    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post not found!!"));
        List<Comment> commentList = commentRepository.findAllByPost(post);
        return commentList.stream().map(this::mapCommentToCommentResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAllCommentsForUser(String username) {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("username not found!!!" + username));
        List<Comment> commentList = commentRepository.findAllByUser(user);
        return commentList.stream().map(this::mapCommentToCommentResponse).collect(Collectors.toList());
    }

    private CommentResponse mapCommentToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .createdDate(comment.getCreatedAt())
                .postId(comment.getPost().getPostId())
                .username(comment.getUser().getUserName())
                .text(comment.getText())
                .build();
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUserName() + "Commented on your post", user.getEmail(), message));
    }

    private Comment mapCommentRequestToComment(CommentRequest commentRequest, Post post, User user) {
        return Comment.builder()
                .text(commentRequest.getText())
                .createdAt(Instant.now())
                .post(post)
                .user(user)
                .build();
    }


}
