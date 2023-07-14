package com.pickupluck.ecogging.domain.plogging.dto;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.plogging.entity.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventDTO extends BaseEntity {
    private Integer eventId ;
    private String title;
    private String content;
    private LocalDate meetingDate;
    private LocalDate endDate;
    private Boolean activate;
    private Integer views;
    private String corpName;
    private Long userId;
    private Long fileId;
    private String location;
    private String explanation;
    private LocalDateTime createdAt;
    private Boolean save;
    private String management;

    public EventDTO(Event event) {
        this.eventId = event.getEventId();
        this.title = event.getTitle();
        this.content = event.getContent();
        this.meetingDate = event.getMeetingDate();
        this.endDate = event.getEndDate();
        this.activate = event.getActive();
        this.views = event.getViews();
        this.corpName = event.getCorpName();
        this.userId = event.getUserId().getId();
        this.fileId = event.getFileId();
        this.location = event.getLocation();
        this.explanation = event.getExplanation();
        this.createdAt = event.getCreatedAt();
        this.save = event.getSave();
        this.management = event.getManagement();
    }

    public EventDTO(String title, String content, LocalDate meetingDate, LocalDate endDate, Boolean activate, Integer views, String corpName,
                    Long userId, Long fileId, String location, String explanation, LocalDateTime createdAt, Boolean save, String management) {
        this.title = title;
        this.content = content;
        this.meetingDate = meetingDate;
        this.endDate = endDate;
        this.activate = activate;
        this.views = views;
        this.corpName = corpName;
        this.userId = userId;
        this.fileId = fileId;
        this.location = location;
        this.explanation = explanation;
        this.createdAt = createdAt;
        this.save = save;
        this.management = management;
    }
}
