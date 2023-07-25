package com.pickupluck.ecogging.domain.user.service;

import com.pickupluck.ecogging.domain.user.dto.*;
import com.pickupluck.ecogging.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User findUserById(Long id) throws Exception;

    User findUserByEmail(String email) throws Exception;

    Boolean isValidEmailForSignup(String email) throws Exception;

    void logout();

    Long signUp(UserSignUpRequestDto userSignUpRequestDto) throws Exception;

    Long corporateSignUp(CorporateSignUpRequest corporateSignUpRequest) throws Exception;

    UserResponseDto getMyUserWithAuthorities() throws Exception;

    UserProfileResponse getCurrentUserProfile() throws Exception;

    UserProfileResponse modifyUserProfile(
            MultipartFile image, UserProfileModifyRequest userInfoModifyRequest) throws Exception;

    CorporateProfileResponse modifyCorporateProfile(
            MultipartFile image, CorporateProfileModifyRequest corporateProfileModifyRequest) throws Exception;

    CorporateProfileResponse getCurrentCorporateProfile() throws Exception;
}
