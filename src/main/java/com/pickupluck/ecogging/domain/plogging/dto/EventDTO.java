package com.pickupluck.ecogging.domain.plogging.dto;

import com.pickupluck.ecogging.domain.BaseEntity;
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
    private Integer save;
    private String corpName;
    private Integer userId;
    private Integer fileId;
    private  String location;
    private  String explanation;
    private LocalDateTime createdAt;

}
