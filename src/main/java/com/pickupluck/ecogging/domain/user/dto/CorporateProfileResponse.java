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

    private String corpName;

    private String corpRepresentative;

    private String corpRegisterNumber;

    public static CorporateProfileResponse from(User user) {
        return CorporateProfileResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .telephone(user.getTelephone())
                .profileImageUrl(user.getProfileImageUrl())
                .notiYn(user.getNotiYn())
                .corpName(user.getCorporate().getCorpName())
                .corpRepresentative(user.getCorporate().getCorpRepresentative())
                .corpRegisterNumber(user.getCorporate().getCorpRegisterNumber())
                .build();
    }
}
