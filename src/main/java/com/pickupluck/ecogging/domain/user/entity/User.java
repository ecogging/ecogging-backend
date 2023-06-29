package com.pickupluck.ecogging.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.pickupluck.ecogging.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    private String nickname;

    private String tel;

    @Column(name = "noti_yn")
    private Boolean isNotificationAllowed;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Builder
    public User(String email, String name, String password, String nickname,
                String tel, Boolean isNotificationAllowed, LoginType loginType) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.tel = tel;
        this.isNotificationAllowed = isNotificationAllowed;
        this.loginType = loginType;
    }
}
