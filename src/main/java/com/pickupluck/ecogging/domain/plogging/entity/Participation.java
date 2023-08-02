package com.pickupluck.ecogging.domain.plogging.entity;

import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Participation {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long participationId ;

    @Column
    private Long userId;

    @Column
    private Boolean confirm;

    @Override
    public String toString() {
        return "Participation{" +
                "participationId=" + participationId +
                ", userId=" + userId +
                ", accompanyId=" + getAccompany().getId() +
                ", confirm=" + confirm +
                '}';
    }

    public Participation(Long userId, Accompany accompany, Boolean confirm) {
        this.userId = userId;
        this.accompany = accompany;
        this.confirm = confirm;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accompanyId")
    private Accompany accompany;
}
