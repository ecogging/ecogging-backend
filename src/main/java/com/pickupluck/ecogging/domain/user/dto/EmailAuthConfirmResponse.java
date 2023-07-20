package com.pickupluck.ecogging.domain.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class EmailAuthConfirmResponse {

    private Boolean isConfirmed;
}
