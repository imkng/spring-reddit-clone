package com.programming.springredditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditRequest {
    private Long id;
    private String name;
    private Integer numberOfPost;
    private String description;

}
