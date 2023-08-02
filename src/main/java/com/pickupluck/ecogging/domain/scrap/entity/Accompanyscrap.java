package com.pickupluck.ecogging.domain.scrap.entity;

import com.pickupluck.ecogging.domain.plogging.entity.Accompany;
import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Accompanyscrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scrapId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="accompany_id")
    private Accompany accompany;

    @Builder
    public Accompanyscrap(User user, Accompany accompany) {
        this.user = user;
        this.accompany = accompany;
    }
}
