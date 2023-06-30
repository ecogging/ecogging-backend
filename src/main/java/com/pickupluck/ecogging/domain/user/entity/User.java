package com.pickupluck.ecogging.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import com.pickupluck.ecogging.domain.BaseEntity;

@Entity
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of="id")
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

    private String tel;

    @Column(name = "noti_yn")
    private String notiYn;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Builder
    public User(String email, String name, String password, String nickname,
                String tel, String notiYn, LoginType loginType) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.tel = tel;
        this.notiYn = notiYn;
        this.loginType = loginType;
    }
}
