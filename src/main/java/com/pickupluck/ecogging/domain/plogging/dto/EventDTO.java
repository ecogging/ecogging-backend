package com.pickupluck.ecogging.domain.plogging.dto;

import com.pickupluck.ecogging.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EventDTO extends BaseEntity {
    private Integer eventId ;
    private String eventTitle;
    private String eventContent;
    private LocalDate eventDay;
    private Boolean activate;
    private Integer views;
    private Integer save;
    private String corpName;
    private Integer userId;
    private Integer fileId;
    private  String location;
    private  String explanation;


}
