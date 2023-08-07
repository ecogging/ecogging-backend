package com.pickupluck.ecogging.domain.user.service;

import java.util.Map;
import java.util.Collections;
import java.util.Optional;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.pickupluck.ecogging.domain.user.entity.LoginType;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import com.pickupluck.ecogging.util.oauth.CustomOAuth2User;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserOAuthService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakaoAccount.get("email");

        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        String nickname = (String) properties.get("nickname");

        String profileImageUrl = (String)properties.get("profile_image");

        Optional<User> findUser = userRepository.findByEmail(email);

        Long userId = null;

        if (findUser.isEmpty()) {
            final User user = User.builder()
                            .email(email)
                            .nickname(nickname)
                            .profileImageUrl(profileImageUrl)
                            .loginType(LoginType.KAKAO)
                            .build();
            final User savedUser = userRepository.save(user);
            userId = savedUser.getId();
        } else {
            log.info("기존 가입된 Kakao 유저입니다. {}", email);
            userId = findUser.get().getId();
            // 로그인하면서 내려주는 정보: 카카오 데이터가 아닌 서비스 DB상 데이터로 변경
            properties.put("nickname", findUser.get().getNickname());
            properties.put("profile_image", findUser.get().getProfileImageUrl());
        }

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                attributes,
                "id",
                userId);
    }

}
