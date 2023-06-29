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
    private Integer participationId ;

    @Column
    private Integer userId;
    @Column
    private Integer accompanyId;
    @Column
    private Integer eventId;
    @Column
    private Integer type;
    @Column
    private Boolean confirm;

    @Override
    public String toString() {
        return "Participation{" +
                "participationId=" + participationId +
                ", userId=" + userId +
                ", accompanyId=" + accompanyId +
                ", eventId=" + eventId +
                ", type=" + type +
                ", confirm=" + confirm +
                '}';
    }

    public Participation(Integer userId, Integer accompanyId, Integer eventId, Integer type, Boolean confirm) {
        this.userId = userId;
        this.accompanyId = accompanyId;
        this.eventId = eventId;
        this.type = type;
        this.confirm = confirm;
    }
}
