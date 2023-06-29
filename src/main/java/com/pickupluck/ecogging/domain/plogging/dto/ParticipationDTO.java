package com.pickupluck.ecogging.domain.plogging.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationDTO {
    private Integer participationId ;
    private Integer userId;
    private Integer accompanyId;
    private Integer eventId;
    private Integer type;
    private Boolean confirm;
}
