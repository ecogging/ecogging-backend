package com.pickupluck.ecogging.domain.user.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailAuthNumberConfirmRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String authNumber;
}
