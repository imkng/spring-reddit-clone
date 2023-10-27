package com.programming.springredditclone.repository;

import com.programming.springredditclone.model.Post;
import com.programming.springredditclone.model.User;
import com.programming.springredditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
