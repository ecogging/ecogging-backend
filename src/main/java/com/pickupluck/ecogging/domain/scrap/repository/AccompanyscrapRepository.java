package com.pickupluck.ecogging.domain.scrap.repository;

import com.pickupluck.ecogging.domain.plogging.entity.Accompany;
import com.pickupluck.ecogging.domain.plogging.entity.Participation;
import com.pickupluck.ecogging.domain.scrap.entity.Accompanyscrap;
import com.pickupluck.ecogging.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccompanyscrapRepository extends JpaRepository<Accompanyscrap, Long> {
    Optional<Accompanyscrap> findByUserAndAccompany(User user, Accompany accompany);
}
