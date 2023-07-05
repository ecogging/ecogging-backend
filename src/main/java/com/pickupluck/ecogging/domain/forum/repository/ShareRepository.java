package com.pickupluck.ecogging.domain.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pickupluck.ecogging.domain.forum.entity.Share;

public interface ShareRepository extends JpaRepository<Share, Long> {
}
