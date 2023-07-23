package com.pickupluck.ecogging.util.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds
            ) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        // secret 데이터 주입 받은 뒤, 디코드하여 저장
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // authentication 으로부터 JWT 토큰 생성 반환
    public String createToken(Authentication authentication) {
        String authorities = "USER"; // todo: 모든 사용자 권한은 유저로 임시 설정

        long now = (new Date()).getTime(); // milliseconds 반환
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    // authentication 으로부터 JWT 토큰 생성 반환
    public String createToken(com.pickupluck.ecogging.domain.user.entity.User user) {
        String authorities = "USER"; // todo: 모든 사용자 권한은 유저로 임시 설정

        long now = (new Date()).getTime(); // milliseconds 반환
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        boolean isCorporate = user.getCorporate() != null;

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("userId", user.getId())
                .claim("nickname", user.getNickname())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        User principal = new User(claims.getSubject(), "", authorities);

        // 인증된 유저, JWT, 인증권한을 가지고 생성된 Authentication 반환
        return new UsernamePasswordAuthenticationToken(principal, jwt, authorities);
    }

    public boolean isValidToken(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Wrong JWT Sign");
        } catch (ExpiredJwtException e) {
            log.info("JWT expired");
        } catch (UnsupportedJwtException e) {
            log.info("unsupported JWT");
        } catch (IllegalArgumentException e) {
            log.info("Wrong JWT");
        }
        return false;
    }


}
