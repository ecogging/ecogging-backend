package com.pickupluck.ecogging.domain.user.service;

import com.pickupluck.ecogging.domain.user.dto.UserLoginRequestDto;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findUserById(Long id) {
        User findUser = userRepository.findById(id).orElseThrow(IllegalArgumentException::new); // todo: custom exception
        return findUser;
    }

    @Override
    public User findUserByEmail(String email) {
        User findUser = userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new); // todo: custom exception
        return findUser;
    }

    @Override
    public void login(UserLoginRequestDto request) {
        // todo
    }

    @Override
    public void logout() {
        // todo
    }

}



