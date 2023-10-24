package com.programming.springredditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private Long id;
    private Long postId;
    private Instant createdDate;
    private String text;
    private String username;
}
