package com.pickupluck.ecogging.domain.plogging.dto;

import com.pickupluck.ecogging.domain.plogging.entity.Accompany;
import com.pickupluck.ecogging.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String nickname;
    private Long userId;
    private String email;
    private String userPicUrl; // 프사 추가
    public AccompanyDTO(Accompany accompany) {
        this.id = accompany.getId();
        this.title = accompany.getTitle();
        this.content = accompany.getContent();
        this.meetingDate = accompany.getMeetingDate();
        this.meetingTime = accompany.getMeetingTime();
        this.numOfPeople = accompany.getNumOfPeople();
        this.active = accompany.getActive();
        this.views = accompany.getViews();
        this.save = accompany.getSave();
        this.location = accompany.getLocation();
        this.locationDetail = accompany.getLocationDetail();
        this.joincnt = accompany.getJoincnt();
        this.createdAt = accompany.getCreatedAt();
        this.updatedAt = accompany.getUpdatedAt();
        if(accompany.getUser()!=null) {
            this.nickname = accompany.getUser().getNickname();
            this.userId = accompany.getUser().getId();
            this.email = accompany.getUser().getEmail();
            this.userPicUrl = accompany.getUser().getProfileImageUrl(); // 프사 추가
        }
    }

}
