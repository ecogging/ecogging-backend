package com.pickupluck.ecogging.domain.plogging.entity;

import com.pickupluck.ecogging.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private Boolean active;
    @Column
    private Integer views;
    @Column
    private Integer save;
    @Column
    private Integer userId;
    @Column
    private Integer fileId;

    @Override
    public String toString() {

        return "Event{" +
                "eventId=" + eventId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", meetingDate=" + meetingDate +
                ", active=" + active +
                ", views=" + views +
                ", save=" + save +
                ", userId=" + userId +
                ", fileId=" + fileId +
                '}';
    }

    @Builder
    public Event(String title, String content, LocalDate meetingDate, LocalDateTime createdAt,
                 Boolean active, Integer views, Integer save, Integer userId, Integer fileId) {
        this.title = title;
        this.content = content;
        this.meetingDate = meetingDate;
        this.createdAt = createdAt;
        this.active = active;
        this.views = views;
        this.save = save;
        this.userId = userId;
        this.fileId = fileId;
    }
}
