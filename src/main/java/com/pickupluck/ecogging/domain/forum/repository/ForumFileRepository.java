package com.pickupluck.ecogging.domain.forum.repository;

import com.pickupluck.ecogging.domain.forum.entity.ForumFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumFileRepository extends JpaRepository<ForumFile, Long> {
}
