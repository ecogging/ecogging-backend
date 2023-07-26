package com.pickupluck.ecogging.domain.user.service;

import com.pickupluck.ecogging.domain.file.dto.AwsS3Dto;
import com.pickupluck.ecogging.domain.file.service.AwsS3Service;
import com.pickupluck.ecogging.domain.user.dto.*;
import com.pickupluck.ecogging.domain.user.entity.Corporate;
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
    public User findUserByEmail(String email) throws Exception {
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new); // todo: custom exception
        return findUser;
    }

    @Transactional
    @Override
    public void logout() {
        // todo
    }

    @Transactional
    @Override
    public Long signUp(UserSignUpRequestDto userDto) throws Exception {
        validateDuplicatedEmail(userDto.getEmail());

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

        User savedUser = userRepository.save(user);
        return savedUser.getId();
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
    public UserProfileResponse modifyUserProfile(
            MultipartFile image, UserProfileModifyRequest userProfileModifyRequest) throws Exception {

        User user = getCurrentUser();
        String userProfileImageUrl = "";

        if (image != null && !image.isEmpty()) {
            AwsS3Dto awsS3Dto = awsS3Service.upload(image, S3_PROFILE_UPLOAD_DIR);
            userProfileImageUrl = awsS3Dto.getAbsolutPath();
        } else {
            userProfileImageUrl = user.getProfileImageUrl();
        }

        user.modifyProfile(userProfileImageUrl, userProfileModifyRequest);

        return UserProfileResponse.from(user);
    }

    @Transactional
    @Override
    public CorporateProfileResponse modifyCorporateProfile(
            MultipartFile image, CorporateProfileModifyRequest corporateProfileModifyRequest) throws Exception {

        User user = getCurrentUser();
        String userProfileImageUrl = "";

        if (image != null && !image.isEmpty()) {
            AwsS3Dto awsS3Dto = awsS3Service.upload(image, S3_PROFILE_UPLOAD_DIR);
            userProfileImageUrl = awsS3Dto.getAbsolutPath();
        } else {
            userProfileImageUrl = user.getProfileImageUrl();
        }

        user.modifyCorporateProfile(userProfileImageUrl, corporateProfileModifyRequest);

        return CorporateProfileResponse.from(user);
    }


    public UserProfileResponse getCurrentUserProfile() throws Exception {
        return UserProfileResponse.from(getCurrentUser());
    }

    public CorporateProfileResponse getCurrentCorporateProfile() throws Exception {
        return CorporateProfileResponse.from(getCurrentUser());
    }

    @Transactional
    @Override
    public Long corporateSignUp(CorporateSignUpRequest request) throws Exception {

        validateDuplicatedEmail(request.getEmail());

        Authority authority = Authority.builder()
                .name("USER")
                .build();

        String defaultProfileImageUrl = getDefaultProfileImageUrl();

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .telephone(request.getTelephone())
                .notiYn("Y")
                .profileImageUrl(defaultProfileImageUrl)
                .build();

        Corporate corporate = Corporate.builder()
                .corpName(request.getCorpName())
                .corpRegisterNumber(request.getCorpRegisterNumber())
                .corpRepresentative(request.getCorpRepresentative())
                .build();

        user.registerCorporate(corporate);
        User savedUser = userRepository.save(user);

        return savedUser.getId();
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

    private void validateDuplicatedEmail(String email) throws Exception {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new Exception("이미 가입되어 있는 유저입니다.");
        }
    }


}



