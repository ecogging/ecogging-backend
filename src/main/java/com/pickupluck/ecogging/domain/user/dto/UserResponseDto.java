package com.pickupluck.ecogging.domain.user.dto;

import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String nickname;

    private String tel;

    private String birthDate;

    private Set<AuthorityDto> authorityDtoSet;

    public static UserResponseDto from(User user) {
        if(user == null) return null;

        return UserResponseDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
