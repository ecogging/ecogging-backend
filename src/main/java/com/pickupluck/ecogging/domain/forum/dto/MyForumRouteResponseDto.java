package com.pickupluck.ecogging.domain.forum.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyForumRouteResponseDto {
    private final Long forumId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final Integer views;
    private final String location;

    @Builder
    public MyForumRouteResponseDto(Long forumId, String title, String content, LocalDateTime createdAt, Integer views, String location) {
        this.forumId = forumId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.views = views;
        this.location = location;
    }
}
