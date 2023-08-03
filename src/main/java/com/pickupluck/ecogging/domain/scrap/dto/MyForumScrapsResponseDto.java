package com.pickupluck.ecogging.domain.scrap.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter // 한꺼번에 가져오는 거니까.. Route & Share 포괄해야 함
public class MyForumScrapsResponseDto {
    private final Long scrapId;

    private final Long forumId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final Integer views;
    private final String type; // 경로 or 나눔

    // User
    private final Long userId;
    private final String nickname;
    private final String userPic;

    // Route
    private final String location;

    // Share
    private final String filePath;

    @Builder
    public MyForumScrapsResponseDto(Long scrapId, Long forumId, String title, String content, LocalDateTime createdAt, Integer views, String type, Long userId, String nickname, String userPic, String location, String filePath) {
        this.scrapId = scrapId;
        this.forumId = forumId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.views = views;
        this.type = type;
        this.userId = userId;
        this.nickname = nickname;
        this.userPic = userPic;
        this.location = location;
        this.filePath = filePath;
    }
}
