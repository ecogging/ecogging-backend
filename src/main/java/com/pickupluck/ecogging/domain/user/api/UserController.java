package com.pickupluck.ecogging.domain.user.api;

import com.pickupluck.ecogging.domain.user.dto.UserProfileModifyRequest;
import com.pickupluck.ecogging.domain.user.dto.UserProfileModifyResponse;
import com.pickupluck.ecogging.domain.user.dto.UserProfileResponse;
import com.pickupluck.ecogging.domain.user.dto.UserResponseDto;
import com.pickupluck.ecogging.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import com.pickupluck.ecogging.domain.file.dto.AwsS3Dto;
import com.pickupluck.ecogging.domain.file.service.AwsS3Service;
import com.pickupluck.ecogging.domain.user.service.UserService;

import java.io.IOException;


/**
 * mypage
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final AwsS3Service awsS3Service;

    private final UserService userService;

    @PostMapping(value = "/mypage", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserProfileModifyResponse> modifyUserProfile(
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart("profile") UserProfileModifyRequest userInfoModifyRequest
    ) throws Exception {
        log.info(userInfoModifyRequest.toString());
        UserProfileModifyResponse response = userService.modifyUserProfile(image, userInfoModifyRequest);

        return new ResponseEntity<UserProfileModifyResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/mypage")
    public ResponseEntity<UserProfileResponse> getMyProfile() throws Exception {
        log.info(userService.getCurrentUserProfile().toString());
        return new ResponseEntity<UserProfileResponse>(userService.getCurrentUserProfile(), HttpStatus.OK);
    }

}
