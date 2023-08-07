package com.pickupluck.ecogging.util.oauth;

import com.pickupluck.ecogging.util.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

//        login 성공한 사용자 목록.
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

//        Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
//        String email = (String) kakaoAccount.get("email");
//        Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");
//        String nickname = (String) properties.get("nickname");
//
//        Long userId = (Long)properties.get("id");

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
