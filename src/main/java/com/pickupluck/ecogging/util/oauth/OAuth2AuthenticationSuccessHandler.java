package com.pickupluck.ecogging.util.oauth;

import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;

import com.pickupluck.ecogging.util.jwt.TokenProvider;


@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String jwt = tokenProvider.createOAuth2KakaoToken(authentication);

        String url = makeRedirectUrl(jwt);

        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.");
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, url);
    }

    private String makeRedirectUrl(String token) {
        return UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect/" + token)
                .build().toUriString();
    }
}
