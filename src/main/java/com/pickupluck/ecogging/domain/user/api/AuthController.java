package com.pickupluck.ecogging.domain.user.api;

import com.pickupluck.ecogging.domain.user.dto.*;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.entity.UserType;
import com.pickupluck.ecogging.util.SecurityUtil;
import com.pickupluck.ecogging.util.mail.MailService;
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

import com.pickupluck.ecogging.domain.user.service.UserService;
import com.pickupluck.ecogging.util.jwt.JwtFilter;
import com.pickupluck.ecogging.util.jwt.TokenProvider;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final MailService mailService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginRequestDto request) {
        try {
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

            LoginResponse loginResponse = LoginResponse.builder()
                    .token(jwt)
                    .userType(UserType.NORMAL)
                    .profileImageUrl(findUser.getProfileImageUrl())
                    .build();

            return new ResponseEntity<>(loginResponse, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth/corporate/login")
    public ResponseEntity<LoginResponse> corporateLogin(@RequestBody UserLoginRequestDto request) {
        String requestEmail = request.getEmail();
        String requestPassword = request.getPassword();

        log.info("corp email: {}",  requestEmail);
        log.info("corpo password: {}", requestPassword);

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(requestEmail, requestPassword);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            User findUser = userService.findUserByEmail(authentication.getName());
            String jwt = tokenProvider.createToken(findUser);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

            LoginResponse loginResponse = LoginResponse.builder()
                    .token(jwt)
                    .userType(UserType.CORPORATE)
                    .profileImageUrl(findUser.getProfileImageUrl())
                    .build();

            return new ResponseEntity<>(loginResponse, httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/auth/signUp")
    public ResponseEntity signUp (
            @Valid @RequestBody UserSignUpRequestDto userDto
    ) {
        try {
            userService.signUp(userDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth/corporate/signUp")
    public ResponseEntity corporateSignUp (
            @Valid @RequestBody CorporateSignUpRequest corporateSignUpRequest
    ) {
        try {
            userService.corporateSignUp(corporateSignUpRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth/email-duplicated")
    public ResponseEntity<EmailValidationResponse> isDuplicatedEmail(@RequestBody EmailValidationRequest emailRequest) {
        try {
            Boolean isValid = userService.isValidEmailForSignup(emailRequest.getEmail());
            log.info(emailRequest.getEmail());
            String message = isValid ? "사용가능한 이메일입니다." : "이미 사용중인 이메일입니다.";

            EmailValidationResponse emailValidationResponse = EmailValidationResponse.builder()
                    .isValid(isValid)
                    .message(message).build();

            return new ResponseEntity<>(emailValidationResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/auth/email-auth-send")
    public ResponseEntity<String> sendAuthorizationEmail(@RequestBody EmailValidationRequest emailRequest) {
        try {
            // generate auth number
            String authNumber = SecurityUtil.generateSixDigitRandomNumber();
            // send email
            String email = emailRequest.getEmail();

            boolean result = mailService.sendSignUpAuthMail(email, authNumber);
            if (result)
                log.info("메일 전송 완료");
            else
                log.info("메일 전송 실패");

            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/auth/email-auth-confirm")
    public ResponseEntity<Boolean> isAuthorizedEmail(@RequestBody EmailAuthNumberConfirmRequest emailAuthConfirmRequest) {

        Boolean isConfirmed = mailService.confirmEmail(emailAuthConfirmRequest);

        log.info("isConfirmed: {}", isConfirmed);

        return new ResponseEntity<>(isConfirmed, HttpStatus.OK);
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
