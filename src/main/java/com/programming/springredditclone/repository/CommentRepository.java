package com.programming.springredditclone.repository;

import com.programming.springredditclone.model.Comment;
import com.programming.springredditclone.model.Post;
import com.programming.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);

    List<Comment> findAllByUser(User user);
}
