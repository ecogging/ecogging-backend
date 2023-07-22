package com.pickupluck.ecogging.domain.user.service;

import com.pickupluck.ecogging.domain.user.dto.*;
import com.pickupluck.ecogging.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    User findUserById(Long id);

    User findUserByEmail(String email);

    Boolean isValidEmailForSignup(String email);

    void logout();

    UserResponseDto signup(UserSignUpRequestDto userDto) throws Exception;

    UserResponseDto getMyUserWithAuthorities() throws Exception;

    UserProfileResponse getCurrentUserProfile() throws Exception;

    UserProfileModifyResponse modifyUserProfile(
            MultipartFile image, UserProfileModifyRequest userInfoModifyRequest) throws Exception;
}
