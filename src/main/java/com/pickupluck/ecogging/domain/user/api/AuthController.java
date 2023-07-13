package com.pickupluck.ecogging.domain.user.api;

import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.pickupluck.ecogging.domain.user.dto.UserLoginRequestDto;
import com.pickupluck.ecogging.domain.user.dto.UserResponseDto;
import com.pickupluck.ecogging.domain.user.dto.UserSignUpRequestDto;
import com.pickupluck.ecogging.domain.user.service.UserService;
import com.pickupluck.ecogging.util.jwt.JwtFilter;
import com.pickupluck.ecogging.util.jwt.TokenDto;
import com.pickupluck.ecogging.util.jwt.TokenProvider;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/auth/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserLoginRequestDto request) {
        String requestEmail = request.getEmail();
        String requestPassword = request.getPassword();

        log.info("email: {}",  requestEmail);
        log.info("password: {}", requestPassword);

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(requestEmail, requestPassword);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User findUser = userService.findUserByEmail(authentication.getName());
        String jwt = tokenProvider.createToken(findUser);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<UserResponseDto> signup (
            @Valid @RequestBody UserSignUpRequestDto userDto
    ) {
        try {
            return ResponseEntity.ok(userService.signup(userDto));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/me")
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserResponseDto> getMyUserInfo(HttpServletRequest request) {
        try {
            return ResponseEntity.ok(userService.getMyUserWithAuthorities());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // todo: 관리자 권한 일단 제외
//    @GetMapping("/user/{username}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable String username) {
//        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
//    }



    @PostMapping("/auth/logout")
    public String logout() {
        return "logout";

    }
}
