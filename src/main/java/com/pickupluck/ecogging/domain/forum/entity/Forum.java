package com.pickupluck.ecogging.domain.forum.entity;

//import jakarta.persistence.*;
//
//import com.pickupluck.ecogging.domain.BaseEntity;
//import com.pickupluck.ecogging.domain.file.entity.File;
//import com.pickupluck.ecogging.domain.user.entity.User;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//
//@Entity
//@Getter
//@Setter
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "FTYPE", discriminatorType = DiscriminatorType.STRING) // forum type
//public abstract class Forum extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "forum_id")
//    private Long id;
//
//    private String title;
//
//    private String content;
//
//    private Integer views;
//
//    private String type;
//
//    @Column(name="route_location")
//    private String routeLocation;
//
//    @Column(name="route_location_detail")
//    private String routeLocationDetail;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    // 이미지
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "file_id")
//    private File imageFile;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Forum {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "forum_id")
    private long id;

    @Column
    private String type;

    @Column
    private String title;

    @Column
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column
    private long views;

    @Column
    private Integer fileId;

    @Column(name = "is_temporary")
    private boolean isTemporary;

    @JoinColumn(name = "user_id")
    private long userId;

    @Column(name = "route_location")
    private String routeLocation;

    @Column(name = "route_location_detail")
    private String routeLocationDetail;

    @Column(name = "accompany_id")
    private Integer accompanyId;


}
