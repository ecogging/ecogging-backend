package com.pickupluck.ecogging.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CorporateProfileModifyRequest {
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
