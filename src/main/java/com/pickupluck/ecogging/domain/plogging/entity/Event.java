package com.pickupluck.ecogging.domain.plogging.entity;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.user.entity.User;
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
    @Column( length = 100000 )
    private String content;
    @Column
    private LocalDate meetingDate;
    @Column
    private LocalDate endDate;
    @Column
    private  String corpName;
    @Column
    private Boolean active;
    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column
    private Long fileId;
    @Column
    private  String location;
    @Column
    private  String explanation;
    @Column
    private  Boolean save;
    @Column
    private String management;


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
                ", userId=" + user.getId() +
                ", fileId=" + fileId +
                ", location='" + location + '\'' +
                ", explanation='" + explanation + '\'' +
                ", save=" + save +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", management=" + management +
                '}';
    }

    public Event(String title, String content, LocalDate meetingDate, LocalDate endDate, String corpName, Boolean active,
                 Integer views, User user, Long fileId, String location, String explanation, Boolean save, String management) {
        this.title = title;
        this.content = content;
        this.meetingDate = meetingDate;
        this.endDate = endDate;
        this.corpName = corpName;
        this.active = active;
        this.views = views;
        this.user =user;
        this.fileId = fileId;
        this.location = location;
        this.explanation = explanation;
        this.save = save;
        this.management = management;
    }
}
