package com.pickupluck.ecogging.domain.plogging.entity;

import com.pickupluck.ecogging.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Event extends BaseEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer eventId ;

    @Column
    private String title;
    @Column
    private String content;
    @Column
    private LocalDate meetingDate;
    @Column
    private LocalDate endDate;
    @Column
    private  String corpName;
    @Column
    private Boolean active;
    @Column
    private Integer views;
    @Column
    private Integer userId;
    @Column
    private Integer fileId;
    @Column
    private  String location;
    @Column
    private  String explanation;

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", meetingDate=" + meetingDate +
                ", endDate=" + endDate +
                ", corpName='" + corpName + '\'' +
                ", active=" + active +
                ", views=" + views +
                ", userId=" + userId +
                ", fileId=" + fileId +
                ", location='" + location + '\'' +
                ", explanation='" + explanation + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public Event(String title, String content, LocalDate meetingDate, LocalDate endDate, String corpName, Boolean active,
                 Integer views, Integer userId, Integer fileId, String location, String explanation) {
        this.title = title;
        this.content = content;
        this.meetingDate = meetingDate;
        this.endDate = endDate;
        this.corpName = corpName;
        this.active = active;
        this.views = views;
        this.userId = userId;
        this.fileId = fileId;
        this.location = location;
        this.explanation = explanation;
    }
}
