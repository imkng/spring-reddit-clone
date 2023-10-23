package com.programming.springredditclone.repository;

import com.programming.springredditclone.dto.PostResponse;
import com.programming.springredditclone.model.Post;
import com.programming.springredditclone.model.Subreddit;
import com.programming.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
