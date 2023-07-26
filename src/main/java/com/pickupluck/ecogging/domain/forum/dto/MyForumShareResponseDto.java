package com.pickupluck.ecogging.domain.forum.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyForumShareResponseDto {
    private final Long forumId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final Integer views;
    private final String fileName;
    private final String filePath;

    @Builder
    public MyForumShareResponseDto(Long forumId, String title, String content, LocalDateTime createdAt, Integer views, String fileName, String filePath) {
        this.forumId = forumId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.views = views;
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
