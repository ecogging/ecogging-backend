package com.pickupluck.ecogging.domain.user.dto;

import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileModifyResponse {

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    @NotBlank
    private String email;

    @NotBlank
    private String telephone;

    @NotBlank
    private String profileImageUrl;

    public static UserProfileModifyResponse from(User user) {
        return UserProfileModifyResponse.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }

}
