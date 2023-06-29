package com.pickupluck.ecogging.domain.plogging.repository;

import com.pickupluck.ecogging.domain.plogging.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}

