package com.pickupluck.ecogging.domain.forum.repository;

import com.pickupluck.ecogging.domain.forum.entity.Sharefile;
import com.pickupluck.ecogging.domain.plogging.entity.Reviewfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareFileRepository extends JpaRepository<Sharefile, Long> {
}
