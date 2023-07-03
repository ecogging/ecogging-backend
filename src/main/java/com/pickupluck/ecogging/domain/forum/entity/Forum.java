package com.pickupluck.ecogging.domain.forum.entity;

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
    private long forumId;
    @Column
    private String type;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private LocalDateTime created_at;
    @Column
    private long views;
    @Column
    private Integer fileId;
    @Column
    private boolean tempId;
    @Column
    private long userId;
    @Column
    private String routeLoc;
    @Column
    private String routeLocDetail;
    @Column
    private Integer accompanyId;

}
