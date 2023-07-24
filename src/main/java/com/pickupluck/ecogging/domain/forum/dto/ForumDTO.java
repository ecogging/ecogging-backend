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

    // 기본생성자
    @Builder
    public ForumDTO(Long forumId, String forumType, Long writerId, String title, String content, Integer views, Boolean isTemp, String routeLocation, String routeLocationDetail, Long thisAccompanyId, String writerNickname, String writerPic, Long fileId, String fileName, String filePath) {
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
    }
}
