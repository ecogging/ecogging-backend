package com.pickupluck.ecogging.domain.plogging.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MainEventResponseDto {
    private final Long evtid; // 행사글 ID
    private final String evtTitle; // 행사글 제목
    private final LocalDate evtStartDate; // 행사 시작 날짜
    private final LocalDate evtEndDate; // 행사 종료 날짜
    private final Boolean active; // 진행 상태
    private final String evtLocation; // 행사 지역
    private final String nickname; // 작성자 닉네임

    private final Long fileId; // 첨부파일 id
    private final String filePath; // 첨부파일 경로

    @Builder
    public MainEventResponseDto(Long evtid, String evtTitle, LocalDate evtStartDate, LocalDate evtEndDate, Boolean active, String evtLocation, String nickname, Long fileId, String filePath) {
        this.evtid = evtid;
        this.evtTitle = evtTitle;
        this.evtStartDate = evtStartDate;
        this.evtEndDate = evtEndDate;
        this.active = active;
        this.evtLocation = evtLocation;
        this.nickname = nickname;
        this.fileId = fileId;
        this.filePath = filePath;
    }
}
