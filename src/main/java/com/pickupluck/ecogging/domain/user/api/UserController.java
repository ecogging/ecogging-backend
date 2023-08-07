package com.pickupluck.ecogging.domain.user.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import com.pickupluck.ecogging.domain.user.service.UserService;
import com.pickupluck.ecogging.domain.user.dto.UserProfileModifyRequest;
import com.pickupluck.ecogging.domain.user.dto.UserProfileResponse;


/**
 * mypage
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/mypage", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserProfileResponse> modifyUserProfile(
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart("profile") UserProfileModifyRequest userInfoModifyRequest
    ) throws Exception {
        log.info(userInfoModifyRequest.toString());
        UserProfileResponse response = userService.modifyUserProfile(image, userInfoModifyRequest);

        return new ResponseEntity<UserProfileResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/mypage")
    public ResponseEntity<UserProfileResponse> getMyProfile() throws Exception {
        log.info(userService.getCurrentUserProfile().toString());
        return new ResponseEntity<UserProfileResponse>(userService.getCurrentUserProfile(), HttpStatus.OK);
    }

}
