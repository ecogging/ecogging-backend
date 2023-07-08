package com.pickupluck.ecogging.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginRequestDto { // todo: length, format validation

    @NotBlank
    private String email;

    private String password;
}
