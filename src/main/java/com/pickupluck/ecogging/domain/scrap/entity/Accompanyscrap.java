package com.pickupluck.ecogging.domain.scrap.entity;

import com.pickupluck.ecogging.domain.plogging.entity.Accompany;
import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Accompanyscrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scrapId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="accompanyId")
    private Accompany accompany;
}
