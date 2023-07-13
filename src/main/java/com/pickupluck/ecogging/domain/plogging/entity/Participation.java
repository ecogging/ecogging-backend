package com.pickupluck.ecogging.domain.plogging.entity;

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
    private Long accompanyId;
    @Column
    private Boolean confirm;

    @Override
    public String toString() {
        return "Participation{" +
                "participationId=" + participationId +
                ", userId=" + userId +
                ", accompanyId=" + accompanyId +
                ", confirm=" + confirm +
                '}';
    }

    public Participation(Long userId, Long accompanyId, Boolean confirm) {
        this.userId = userId;
        this.accompanyId = accompanyId;
        this.confirm = confirm;
    }
}
