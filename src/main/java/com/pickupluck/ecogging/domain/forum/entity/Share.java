package com.pickupluck.ecogging.domain.forum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Forum")
public class Share{
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
    private Integer views;

    @Column(name="file_id")
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
