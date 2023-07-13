package com.pickupluck.ecogging.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String nickname;

    private String tel;

    private String birthDate;
}
