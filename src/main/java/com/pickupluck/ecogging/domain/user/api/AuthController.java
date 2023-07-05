package com.pickupluck.ecogging.domain.user.api;

import com.pickupluck.ecogging.domain.user.dto.UserLoginRequestDto;
import com.pickupluck.ecogging.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * signup, login, logout
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/auth/login")
    public String login(@RequestBody UserLoginRequestDto request) {
        userService.login(request);
        log.info(request.toString());
        return "login";
    }

    @PostMapping("/auth/signup")
    public String signUp() {
        return "signUp";
    }

    @PostMapping("/auth/logout")
    public String logout() {
        return "logout";
    }
}
