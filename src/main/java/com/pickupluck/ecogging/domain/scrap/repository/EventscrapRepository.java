package com.pickupluck.ecogging.domain.scrap.repository;

import com.pickupluck.ecogging.domain.plogging.entity.Event;
import com.pickupluck.ecogging.domain.scrap.entity.Eventscrap;
import com.pickupluck.ecogging.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventscrapRepository extends JpaRepository<Eventscrap, Long> {
    Optional<Eventscrap> findByUserAndEvent(User user, Event event);
    Page<Eventscrap> findByUserId(Long userId, PageRequest paging);
}
