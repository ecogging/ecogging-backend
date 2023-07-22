package com.pickupluck.ecogging.domain.user.service;

import com.pickupluck.ecogging.domain.file.dto.AwsS3Dto;
import com.pickupluck.ecogging.domain.file.service.AwsS3Service;
import com.pickupluck.ecogging.domain.user.dto.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.pickupluck.ecogging.domain.user.entity.Authority;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import com.pickupluck.ecogging.util.SecurityUtil;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final static String S3_PROFILE_UPLOAD_DIR = "profile";

    private final static String S3_DEFAULT_PROFILE_IMAGE_NAME = "profile-default.png";

    private final AwsS3Service awsS3Service;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User findUserById(Long id) {
        User findUser = userRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new); // todo: custom exception
        return findUser;
    }

    @Override
    public Boolean isValidEmailForSignup(String email) {
        return !userRepository.existsByEmail(email);
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

        String defaultProfileImageUrl = getDefaultProfileImageUrl();

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .telephone(userDto.getTelephone())
                .notiYn("Y")
                .profileImageUrl(defaultProfileImageUrl)
                .build();

        return UserResponseDto.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserWithAuthorities(String email) {
        return UserResponseDto.from(userRepository.findByEmail(email).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserResponseDto getMyUserWithAuthorities() throws Exception {
        return UserResponseDto.from(getCurrentUser());
    }

    @Transactional
    @Override
    public UserProfileModifyResponse modifyUserProfile(
            MultipartFile image, UserProfileModifyRequest userInfoModifyRequest) throws Exception {

        User user = getCurrentUser();
        String userProfileImageUrl = "";

        if (image != null && !image.isEmpty()) {
            AwsS3Dto awsS3Dto = awsS3Service.upload(image, S3_PROFILE_UPLOAD_DIR);
            userProfileImageUrl = awsS3Dto.getAbsolutPath();
        } else {
            userProfileImageUrl = user.getProfileImageUrl();
        }

        user.modifyProfile(userProfileImageUrl, userInfoModifyRequest);

        return UserProfileModifyResponse.from(user);
    }

    public UserProfileResponse getCurrentUserProfile() throws Exception {
        return UserProfileResponse.from(getCurrentUser());
    }

    private User getCurrentUser() throws Exception {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findByEmail)
                .orElseThrow(() -> new Exception("Member not found"));
    }

    private String getDefaultProfileImageUrl() {
        return awsS3Service.getExistImageUrl(
                String.join("/", S3_PROFILE_UPLOAD_DIR, S3_DEFAULT_PROFILE_IMAGE_NAME));
    }
}



