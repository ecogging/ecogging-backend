package com.pickupluck.ecogging.domain.plogging.repository;

import com.pickupluck.ecogging.domain.plogging.entity.Accompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccompanyRepository extends JpaRepository<Accompany, Long> {

    Page<Accompany> findBySaveFalseAndActiveTrue(PageRequest paging); //저장완료 & 모집중

    Page<Accompany> findBySaveFalseAndActiveFalse(PageRequest paging); //저장완료 & 모집완료

    List<Accompany> findTop3ByOrderByCreatedAtDesc(); // 메인 최신순 상위 3개 글

    Page<Accompany> findByUserId(Long userId, PageRequest paging);
    List<Accompany> findByUserId(Long userId);
    Page<Accompany> findByUserIdAndSaveTrue(Long userId, PageRequest paging);
}
