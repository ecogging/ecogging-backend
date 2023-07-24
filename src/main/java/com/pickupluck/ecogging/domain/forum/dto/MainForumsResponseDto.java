package com.pickupluck.ecogging.domain.forum.dto;

import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MainForumsResponseDto {
    private final Long forumId; // forum
    private final String type; // 글 타입 - 후기, 경로, 나눔
    private final String title; // 글 제목
    private final String content; // 글 내용
    private final LocalDateTime createdAt; // 생성일시
    private final Integer views; // 조회수
    private final String userNickname; // 작성자 닉네임
    private final Long userId; // 작성자 Id

    public MainForumsResponseDto(Long forumId, String type, String title, String content, LocalDateTime createdAt, Integer views, String userNickname, Long userId) {
        this.forumId = forumId;
        this.type = type;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.views = views;
        this.userNickname = userNickname;
        this.userId = userId;
    }
}



