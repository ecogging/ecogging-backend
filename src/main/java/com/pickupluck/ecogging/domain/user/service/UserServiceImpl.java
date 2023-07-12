package com.pickupluck.ecogging.domain.user.service;

import com.pickupluck.ecogging.domain.user.dto.UserResponseDto;
import com.pickupluck.ecogging.domain.user.dto.UserSignUpRequestDto;
import com.pickupluck.ecogging.domain.user.entity.Authority;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import com.pickupluck.ecogging.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findUserById(Long id) {
        User findUser = userRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new); // todo: custom exception
        return findUser;
    }

    @Override
    public User findUserByEmail(String email) {
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new); // todo: custom exception
        return findUser;
    }

    @Override
    public void logout() {
        // todo
    }


    @Transactional
    public UserResponseDto signup(UserSignUpRequestDto userDto) throws Exception {
        if (userRepository.findByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new Exception("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .name("USER")
                .build();

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .tel(userDto.getTel())
                .build();

        return UserResponseDto.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserWithAuthorities(String username) {
        return UserResponseDto.from(userRepository.findByEmail(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserResponseDto getMyUserWithAuthorities() throws Exception {
        return UserResponseDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findByEmail)
                        .orElseThrow(() -> new Exception("Member not found"))
        );
    }
}



