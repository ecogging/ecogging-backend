package com.pickupluck.ecogging.domain.scrap.repository;

import com.pickupluck.ecogging.domain.plogging.entity.Event;
import com.pickupluck.ecogging.domain.scrap.entity.Eventscrap;
import com.pickupluck.ecogging.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventscrapRepository extends JpaRepository<Eventscrap, Integer> {
    Optional<Eventscrap> findByUserScrapAndEventScrap(User user, Event event);
    
}
