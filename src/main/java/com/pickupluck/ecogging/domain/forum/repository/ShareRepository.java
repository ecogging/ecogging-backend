package com.pickupluck.ecogging.domain.forum.repository;

import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.plogging.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pickupluck.ecogging.domain.forum.entity.Share;

public interface ShareRepository extends JpaRepository<Share, Long> {
    Page<Share> findAllByType(String 나눔, PageRequest pageRequest, Sort sortByCreateAtDesc);
//    Page<Share> findAll(Pageable pageable);

//    Page<Share> findAllByType(String 나눔, PageRequest pageRequest);
}
