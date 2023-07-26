package com.pickupluck.ecogging.domain.forum.dto;

import com.pickupluck.ecogging.domain.forum.entity.Forum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class ForumDTO {

    // 기본
    private Long forumId; // 포럼글 ID
    private String forumType; // 글타입
    private Long writerId; // 작성자
    private String title; // 제목
    private String content; // 내용
    private Integer views; // 조회수
    private Boolean isTemp; // 임시저장 여부

    private String routeLocation; // 경로추천 - 위치
    private String routeLocationDetail; // 경로추천 - 상세 위치
    private Long thisAccompanyId; // 후기 - 해당 플로깅 모임글
    private String writerNickname; // 작성자 닉네임
    private String writerPic; // 작성자 프로필 사진 url

    // 나눔 첨부파일 관련
    private Long fileId; // 첨부파일 ID
    private String fileName; // 첨부파일 이름
    private String filePath; // 첨부파일 경로

    // 날짜
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 기본생성자
    @Builder
    public ForumDTO(Long forumId, String forumType, Long writerId, String title, String content, Integer views, Boolean isTemp, String routeLocation, String routeLocationDetail, Long thisAccompanyId, String writerNickname, String writerPic, Long fileId, String fileName, String filePath, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.forumId = forumId;
        this.forumType = forumType;
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.views = views;
        this.isTemp = isTemp;
        this.routeLocation = routeLocation;
        this.routeLocationDetail = routeLocationDetail;
        this.thisAccompanyId = thisAccompanyId;
        this.writerNickname = writerNickname;
        this.writerPic = writerPic;
        this.fileId = fileId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ForumDTO(Forum forum) {
        this.forumId = forum.getId();
        this.forumType = forum.getType();
        this.writerId = forum.getWriter().getId();
        this.title = forum.getTitle();
        this.content = forum.getContent();
        this.views = forum.getViews();
        this.isTemp = forum.getIsTemporary();
        this.routeLocation = forum.getRouteLocation();
        this.routeLocationDetail = forum.getRouteLocationDetail();
        this.thisAccompanyId = forum.getThisAccompany().getId();
        if(forum.getWriter()!=null) {
            this.writerNickname = forum.getWriter().getNickname();
            this.writerPic = forum.getWriter().getProfileImageUrl();
        }
        if(forum.getFile()!=null) {
            this.fileId = forum.getFile().getId();
            this.fileName = forum.getFile().getFileName();
            this.filePath = forum.getFile().getPath();
        }
        this.createdAt = forum.getCreatedAt();
        this.updatedAt = forum.getUpdatedAt();
    }
}
