package com.pickupluck.ecogging.domain.forum.dto;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.forum.entity.ForumFile;
import com.pickupluck.ecogging.domain.forum.entity.Share;
import com.pickupluck.ecogging.domain.plogging.entity.Accompany;
import com.pickupluck.ecogging.domain.plogging.entity.Review;
import com.pickupluck.ecogging.domain.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
public class ForumDTO {

    // 기본
    private Long forumId; // 포럼글 ID
    private String forumType; // 글타입
    private User writer; // 작성자
    private String title; // 제목
    private String content; // 내용
    private Integer views; // 조회수
    private ForumFile forumFile; // 첨부파일
    private Boolean isTemp; // 임시저장 여부
    private String routeLocation; // 경로추천 - 위치
    private String routeLocationDetail; // 경로추천 - 상세 위치
    private Accompany thisAccompany; // 후기 - 해당 플로깅 모임글

    // 작성자 관련
    private Long writerId; // 작성자 ID
    private String writerNickname; // 작성자 닉네임
    private String writerPic; // 작성자 프로필 사진 url

    // 나눔 첨부파일 관련
    private Long fileId; // 첨부파일 ID
    private String fileName; // 첨부파일 이름
    private String filePath; // 첨부파일 경로

    // 후기 관련
    private Long forumIdForReview; // 후기쓰는 플로깅 모임글 ID

    // 1. 후기
    @Builder
    public ForumDTO(Long forumId, String forumType, User writer, String title, String content, Integer views, ForumFile forumFile, Boolean isTemp, Accompany thisAccompany, Long writerId, String writerNickname, String writerPic, Long fileId, String fileName, String filePath, Long forumIdForReview) {
        this.forumId = forumId;
        this.forumType = forumType;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.views = views;
        this.forumFile = forumFile;
        this.isTemp = isTemp;
        this.thisAccompany = thisAccompany;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writerPic = writerPic;
        this.fileId = fileId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.forumIdForReview = forumIdForReview;
    }

    // 2. 나눔
    @Builder
    public ForumDTO(Long forumId, String forumType, User writer, String title, String content, Integer views, ForumFile forumFile, Boolean isTemp, Long writerId, String writerNickname, String writerPic, Long fileId, String fileName, String filePath) {
        this.forumId = forumId;
        this.forumType = forumType;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.views = views;
        this.forumFile = forumFile;
        this.isTemp = isTemp;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writerPic = writerPic;
        this.fileId = fileId;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    // 3. 경로추천
    @Builder
    public ForumDTO(Long forumId, String forumType, User writer, String title, String content, Integer views, Boolean isTemp, String routeLocation, String routeLocationDetail, Long writerId, String writerNickname, String writerPic) {
        this.forumId = forumId;
        this.forumType = forumType;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.views = views;
        this.isTemp = isTemp;
        this.routeLocation = routeLocation;
        this.routeLocationDetail = routeLocationDetail;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writerPic = writerPic;
    }

    // 4. 기본생성자
    @Builder
    public ForumDTO(Long forumId, String forumType, User writer, String title, String content, Integer views, ForumFile forumFile, Boolean isTemp, String routeLocation, String routeLocationDetail, Accompany thisAccompany, Long writerId, String writerNickname, String writerPic, Long fileId, String fileName, String filePath, Long forumIdForReview) {
        this.forumId = forumId;
        this.forumType = forumType;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.views = views;
        this.forumFile = forumFile;
        this.isTemp = isTemp;
        this.routeLocation = routeLocation;
        this.routeLocationDetail = routeLocationDetail;
        this.thisAccompany = thisAccompany;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writerPic = writerPic;
        this.fileId = fileId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.forumIdForReview = forumIdForReview;
    }
  
}
