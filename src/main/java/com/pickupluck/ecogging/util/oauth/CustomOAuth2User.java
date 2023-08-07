package com.pickupluck.ecogging.util.oauth;

import java.util.Map;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import lombok.Getter;


@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private Long userId;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes,
                            String nameAttributeKey,
                            Long userId) {
        super(authorities, attributes, nameAttributeKey);
        registerUserId(userId);
    }

    private void registerUserId(Long userId) {
        this.userId = userId;
    }
}
