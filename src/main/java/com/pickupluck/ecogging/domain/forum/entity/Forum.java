package com.pickupluck.ecogging.domain.forum.entity;

import jakarta.persistence.*;

import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.file.entity.File;
import com.pickupluck.ecogging.domain.user.entity.User;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FTYPE", discriminatorType = DiscriminatorType.STRING) // forum type
public abstract class Forum extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forum_id")
    private Long id;

    private String title;

    private String content;

    private Integer views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 이미지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File imageFile;
}
