package com.pickupluck.ecogging.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginResponse { // 나중에 nickname 등 인증 이외 정보도 여기로

    private String token;

    private String profileImageUrl;
}
