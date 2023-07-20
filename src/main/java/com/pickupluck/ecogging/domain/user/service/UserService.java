package com.pickupluck.ecogging.domain.user.service;

import com.pickupluck.ecogging.domain.user.dto.UserLoginRequestDto;
import com.pickupluck.ecogging.domain.user.dto.UserResponseDto;
import com.pickupluck.ecogging.domain.user.dto.UserSignUpRequestDto;
import com.pickupluck.ecogging.domain.user.entity.User;

public interface UserService {

    User findUserById(Long id);

    User findUserByEmail(String email);

    Boolean isValidEmailForSignup(String email);

    void logout();

    UserResponseDto signup(UserSignUpRequestDto userDto) throws Exception;

    UserResponseDto getMyUserWithAuthorities() throws Exception;
}
