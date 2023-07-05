package com.pickupluck.ecogging.domain.forum.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
public class Route extends Forum {

    private String routeLocation;

    private String routeLocationDetail;
}
