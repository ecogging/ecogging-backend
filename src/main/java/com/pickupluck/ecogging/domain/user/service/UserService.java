package com.pickupluck.ecogging.domain.user.service;

import com.pickupluck.ecogging.domain.user.dto.UserLoginRequestDto;
import com.pickupluck.ecogging.domain.user.entity.User;

public interface UserService {
    // todo: front에 맞는 User DTO 생성
    User findUserById(Long id);
    User findUserByEmail(String email);

    // todo: login 결과로 token
    void login(UserLoginRequestDto request);
    void logout();
}
