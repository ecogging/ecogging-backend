package com.pickupluck.ecogging.domain.user.dto;

import com.pickupluck.ecogging.domain.user.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CorporateProfileResponse {
    private String email;

    private String name;

    private String nickname;

    private String telephone;

    private String profileImageUrl;

    private String notiYn;

    private String corporateName;

    private String representative;

    private String registerNumber;

    public static CorporateProfileResponse from(User user) {
        return CorporateProfileResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .telephone(user.getTelephone())
                .profileImageUrl(user.getProfileImageUrl())
                .notiYn(user.getNotiYn())
                .corporateName(user.getCorporate().getName())
                .representative(user.getCorporate().getRepresentative())
                .registerNumber(user.getCorporate().getRegisterNumber())
                .build();
    }
}
