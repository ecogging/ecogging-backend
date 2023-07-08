package com.pickupluck.ecogging.domain.scrap.entity;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.plogging.entity.Event;
import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Scrap extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scrapId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private User userScrap;

    @Column
    private String targetType;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="eventId")
    private Event eventScrap;
}
