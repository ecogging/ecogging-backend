package com.pickupluck.ecogging.domain.plogging.repository;

import com.pickupluck.ecogging.domain.plogging.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByType(String 후기, PageRequest pageRequest, Sort sortByCreateAtDesc);
//    Page<Review> findAll(Pageable pageable);

//    Page<Review> findAllByType(String 후기, PageRequest pageRequest);

//    Page<Review> findAllByTypeOrderByDesc(String 후기, PageRequest pageRequest);

//    Page<Review> findAllByTypeOrderByIdDesc(String 후기, PageRequest pageRequest);
}
