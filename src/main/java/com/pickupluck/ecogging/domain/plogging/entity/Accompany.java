package com.pickupluck.ecogging.domain.plogging.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.pickupluck.ecogging.domain.user.entity.Authority;
import jakarta.persistence.*;

import lombok.*;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.user.entity.User;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "user")
@DynamicInsert
public class Accompany extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="accompany_id")
    private Long id;

    private String title;

    private String content;

    private LocalDate meetingDate;

    private LocalTime meetingTime;

    private Integer numOfPeople;

    @ColumnDefault("true")
    private Boolean active;

    @ColumnDefault("0")
    private Integer views;

    private Boolean save;

    private String location;

    private String locationDetail;

    @ColumnDefault("0")
    private  Integer joincnt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @Builder
    public Accompany(String title, String content, LocalDate meetingDate, LocalTime meetingTime, Integer numOfPeople, Boolean active, Integer views, Boolean save, User writer, String location, String locationDetail) {
        this.title = title;
        this.content = content;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.numOfPeople = numOfPeople;
        this.active = active;
        this.views = views;
        this.save = save;
        this.user = writer;
        this.location = location;
        this.locationDetail = locationDetail;
    }
}
