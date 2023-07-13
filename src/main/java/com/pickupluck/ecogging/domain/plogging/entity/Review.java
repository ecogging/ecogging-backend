package com.pickupluck.ecogging.domain.plogging.entity;


import jakarta.persistence.*;

import com.pickupluck.ecogging.domain.forum.entity.Forum;

@Entity
public class Review extends Forum {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accompany_id")
    private Accompany accompany;

    // 임시저장 여부
    @Column(name = "is_temporary")
    private Boolean isTemporary;

}
