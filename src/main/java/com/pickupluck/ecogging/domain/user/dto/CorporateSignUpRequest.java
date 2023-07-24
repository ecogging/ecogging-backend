package com.pickupluck.ecogging.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CorporateSignUpRequest {

    // corporate
    @NotBlank
    private String corpName;

    @NotBlank
    private String corpRegisterNumber;

    @NotBlank
    private String corpRepresentative;

    // user
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    private String telephone;
}
