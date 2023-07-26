package com.pickupluck.ecogging.domain.scrap.repository;

import com.pickupluck.ecogging.domain.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ForumscrapRepository extends JpaRepository<Scrap, Long> {

    Optional<Scrap>  findByForumIdAndUserId(Long forumId, Long userId);
}
