package com.pickupluck.ecogging.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileModifyRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @NotBlank
    private String email;

    @NotBlank
    private String telephone;
}
