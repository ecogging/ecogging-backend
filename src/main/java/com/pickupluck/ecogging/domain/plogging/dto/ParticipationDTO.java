package com.pickupluck.ecogging.domain.plogging.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParticipationDTO {
    private Integer participationId ;
    private Integer userId;
    private Integer accompanyId;
    private Integer eventId;
    private Integer type;
    private Boolean confirm;

    @Override
    public String toString() {
        return "ParticipationDTO{" +
                "participationId=" + participationId +
                ", userId=" + userId +
                ", accompanyId=" + accompanyId +
                ", eventId=" + eventId +
                ", type=" + type +
                ", confirm=" + confirm +
                '}';
    }

    public ParticipationDTO(Integer userId, Integer accompanyId, Integer eventId, Integer type, Boolean confirm) {
        this.userId = userId;
        this.accompanyId = accompanyId;
        this.eventId = eventId;
        this.type = type;
        this.confirm = confirm;
    }
}
