package com.pickupluck.ecogging.domain.user.dto;

import com.pickupluck.ecogging.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileResponse {

    private String email;

    private String name;

    private String nickname;

    private String telephone;

    private String profileImageUrl;

    private String notiYn;

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .telephone(user.getTelephone())
                .profileImageUrl(user.getProfileImageUrl())
                .notiYn(user.getNotiYn())
                .build();
    }

}
