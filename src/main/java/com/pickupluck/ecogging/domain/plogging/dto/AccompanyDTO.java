package com.pickupluck.ecogging.domain.plogging.dto;

import com.pickupluck.ecogging.domain.user.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class AccompanyDTO {
    private Long id;

    private String title;

    private String content;

    private LocalDate meetingDate;

    private LocalTime meetingTime;

    private Integer numOfPeople;

    private Boolean active;

    private Integer views;

    private Boolean save;

    private String location;

    private String locationDetail;

    private  Integer joincnt;

    private String nickname;

}
