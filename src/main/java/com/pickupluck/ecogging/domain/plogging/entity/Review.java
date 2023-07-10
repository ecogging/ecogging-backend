package com.pickupluck.ecogging.domain.plogging.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.pickupluck.ecogging.domain.forum.entity.Forum;

@Entity
public class Review extends Forum {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accompany_id")
    private Accompany accompany;

}
