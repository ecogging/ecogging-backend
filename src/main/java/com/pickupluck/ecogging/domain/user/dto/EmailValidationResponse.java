package com.pickupluck.ecogging.domain.user.dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
public class EmailValidationResponse {

    private Boolean isValid;

    private String message;
}
