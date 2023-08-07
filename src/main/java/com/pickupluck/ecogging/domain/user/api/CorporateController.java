package com.pickupluck.ecogging.domain.user.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.pickupluck.ecogging.domain.user.dto.*;
import com.pickupluck.ecogging.domain.user.service.UserService;


@Slf4j
@RestController
@RequiredArgsConstructor
public class CorporateController {

    private final UserService userService;

    @PostMapping(value = "/corporate/mypage", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CorporateProfileResponse> modifyCorporateProfile(
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart("profile") CorporateProfileModifyRequest corporateProfileModifyRequest
    ) throws Exception {
        log.info(corporateProfileModifyRequest.toString());
        CorporateProfileResponse response = userService.modifyCorporateProfile(image, corporateProfileModifyRequest);

        return new ResponseEntity<CorporateProfileResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/corporate/mypage")
    public ResponseEntity<CorporateProfileResponse> getMyProfile() throws Exception {
        log.info(userService.getCurrentUserProfile().toString());
        return new ResponseEntity<CorporateProfileResponse>(userService.getCurrentCorporateProfile(), HttpStatus.OK);
    }

}
