package com.pickupluck.ecogging.util.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    public static String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;

    // JWT 인증을 위한 필터 구현
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest))
            throw new ServletException("request is not instance of HttpServletRequest");

        HttpServletRequest httpServletRequest = (HttpServletRequest)request;

        String jwt = resolveToken(httpServletRequest);
        String requestUri = httpServletRequest.getRequestURI();

        // 정상적인 토큰이라면, 인증 정보를 현재 SecurityContext에 저장하는 역할
        if (StringUtils.hasText(jwt) && tokenProvider.isValidToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("register Authentication '{}' to Security Context. URI: {}", authentication.getName(), requestUri);
        } else {
            log.info("No valid JWT. URI: {}", requestUri);
        }

        chain.doFilter(request, response);
    }

    // request로부터 JWT(string) 추출
    private String resolveToken(HttpServletRequest request) {
        final String jwtPrefix = "Bearer ";
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtPrefix)) {
            return bearerToken.substring(jwtPrefix.length());
        }

        return null;
    }
}
