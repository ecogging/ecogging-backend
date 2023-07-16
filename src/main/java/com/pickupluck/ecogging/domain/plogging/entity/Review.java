package com.pickupluck.ecogging.domain.plogging.entity;


//import jakarta.persistence.*;
//
//import com.pickupluck.ecogging.domain.forum.entity.Forum;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@NoArgsConstructor
//public class Review extends Forum {
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "accompany_id")
//    private Accompany accompany;
//
//    // 임시저장 여부
//    @Column(name = "is_temporary")
//    private Boolean isTemporary;

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
@Table(name = "Forum")
public class Review {

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

    public Boolean getIsTemporary() {
        return this.isTemporary;
    }
}
