package com.pickupluck.ecogging.domain.user.entity;

import com.pickupluck.ecogging.domain.plogging.entity.Accompany;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import com.pickupluck.ecogging.domain.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

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

    private String tel;

    private String notiYn;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @OneToMany(mappedBy = "user")
    private List<Authority> authorities = new ArrayList<>();

    private String profileImageUrl;

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

    public void updateNotiYn(String notiYn) {
        if (hasText(notiYn)) {
            this.notiYn = notiYn;
        }
    }

    @Override
    public String toString() {
        return String.format("[%s, %s, %s, %s, %s]", id, email, name, nickname, tel);
    }

    public void changeProfileImageUrl(String imageUrl) {
        if (hasText(imageUrl)) {
            this.profileImageUrl = imageUrl;
        }
    }
}
