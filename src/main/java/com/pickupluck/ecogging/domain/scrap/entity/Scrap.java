package com.pickupluck.ecogging.domain.scrap.entity;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Scrap extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scrapId;

    private Long userId;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="user_id")
//    private User userScrap;
//
//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="eventId")
//    private Event eventScrap;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="forum_id")
    private Forum forum;

    @Builder
    public Scrap(Long scrapId, Long userId, Forum forum) {
        this.scrapId=scrapId;
        this.userId=userId;
//        this.userScrap=userScrap;
//        this.eventScrap=eventScrap;
        this.forum=forum;
    }

}
