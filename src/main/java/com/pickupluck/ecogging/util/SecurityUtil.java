package com.pickupluck.ecogging.util;

import java.util.Optional;
import java.util.Random;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() {}

    public static String generateSixDigitRandomNumber() {
        Random random = new Random();

        // Generate a random number between 100000 and 999999 (inclusive)
        int randomNumber = random.nextInt(900000) + 100000;

        // Convert the number to a six-character string
        String sixDigitRandomNumber = String.valueOf(randomNumber);

        return sixDigitRandomNumber;
    }

    public static Optional<String> getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String username = "";

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            username = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(username);
    }
}