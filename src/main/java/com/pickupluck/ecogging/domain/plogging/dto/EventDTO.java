package com.pickupluck.ecogging.domain.plogging.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Integer eventId ;
    private String eventTitle;
    private String eventContent;
    private Date eventDay;
    private  Date createdAt;
    private Boolean activate;
    private Integer views;
    private Integer save;
    private Integer userId;
    private Integer fileId;

    @Override
    public String toString() {
        return "EventDTO{" +
                "eventId=" + eventId +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventContent='" + eventContent + '\'' +
                ", eventDay=" + eventDay +
                ", createdAt=" + createdAt +
                ", activate=" + activate +
                ", views=" + views +
                ", save=" + save +
                ", userId=" + userId +
                ", fileId=" + fileId +
                '}';
    }
}
