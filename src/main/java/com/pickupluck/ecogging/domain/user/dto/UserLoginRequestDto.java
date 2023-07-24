package com.pickupluck.ecogging.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginRequestDto { // todo: length, format validation

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
