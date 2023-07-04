package com.pickupluck.ecogging.domain.plogging.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;

import lombok.*;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of="id", callSuper = false)
@ToString(exclude = "writer")
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

    private Boolean active;

    private Integer views;

    private Boolean save;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @Builder
    public Accompany(String title, String content, LocalDate meetingDate, LocalTime meetingTime, Integer numOfPeople, Boolean active, Integer views, Boolean save, User writer) {
        this.title = title;
        this.content = content;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.numOfPeople = numOfPeople;
        this.active = active;
        this.views = views;
        this.save = save;
        this.writer = writer;
    }
}
