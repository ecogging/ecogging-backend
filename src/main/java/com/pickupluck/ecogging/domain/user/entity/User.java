package com.pickupluck.ecogging.domain.user.entity;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import static org.springframework.util.StringUtils.hasText;

import lombok.*;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.user.dto.CorporateProfileModifyRequest;
import com.pickupluck.ecogging.domain.user.dto.UserProfileModifyRequest;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of="id", callSuper = false)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    private String nickname;

    private String telephone;

    private String notiYn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "corporate_id")
    private Corporate corporate;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @OneToMany(mappedBy = "user")
    private List<Authority> authorities = new ArrayList<>();

    private String profileImageUrl;

    @Builder
    public User(String email, String name, String password, String nickname,
                String telephone, String notiYn, LoginType loginType, String profileImageUrl) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.telephone = telephone;
        this.notiYn = notiYn;
        this.loginType = loginType;
        this.profileImageUrl = profileImageUrl;
    }

    public void updateNotiYn(String notiYn) {
        if (hasText(notiYn)) {
            this.notiYn = notiYn;
        }
    }

    @Override
    public String toString() {
        return String.format("[%s, %s, %s, %s, %s]", id, email, name, nickname, telephone);
    }

    public void changeProfileImageUrl(String imageUrl) {
        if (hasText(imageUrl)) {
            this.profileImageUrl = imageUrl;
        }
    }

    public void modifyProfile(String profileImageUrl, UserProfileModifyRequest userInfoModifyRequest) {
        this.email = userInfoModifyRequest.getEmail();
        this.name = userInfoModifyRequest.getName();
        this.nickname = userInfoModifyRequest.getNickname();
        this.telephone = userInfoModifyRequest.getTelephone();
        changeProfileImageUrl(profileImageUrl);
    }

    public void modifyCorporateProfile(String profileImageUrl, CorporateProfileModifyRequest request) {
        this.email = request.getEmail();
        this.nickname = request.getNickname();
        this.telephone = request.getTelephone();
        this.corporate.modifyInformation(request);

        changeProfileImageUrl(profileImageUrl);
    }

    public void registerCorporate(Corporate corporate) {
        corporate.registerManager(this);
        this.corporate = corporate;
    }
}
