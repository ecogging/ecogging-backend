package com.pickupluck.ecogging.domain.forum.repository;

import com.pickupluck.ecogging.domain.forum.dto.MainForumsResponseDto;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum,Long> {

    // RouteRepository 에서 옮겨온거 -- 메서드 이름 중복이라 ForRoute, ForShare 붙였어요

    @Query("select f from Forum f where f.type in ('나눔','경로','후기')")
    Page<Forum> findAllByType(Pageable pageable);

//    @Query("select f from Forum f where f.type in ('나눔','경로','후기')")
//    Page<Forum> findAllByType(String type, Pageable pageable);

//    @Query("SELECT f FROM Forum f WHERE f.type = :type")
//    Page<Forum> findAllByType(String type, Pageable pageable);

    @Query("SELECT f FROM Forum f WHERE f.type=:type")
    Page<Forum> findAllByType(@Param("type")String type, Pageable pageable);


    // ShareRepository 에서 옮겨온거
//    Page<Forum> findAllByType(String 나눔, PageRequest pageRequest, Sort sortByCreateAtDesc);
//    Page<Share> findAll(Pageable pageable);

//    Page<Share> findAllByType(String 나눔, PageRequest pageRequest);


    // ReviewRepository 에서 옮겨온거
//    Page<Review> findAllByType(String 후기, PageRequest pageRequest, Sort sortByCreateAtDesc);
//    Page<Review> findAll(Pageable pageable);

//    Page<Review> findAllByType(String 후기, PageRequest pageRequest);

//    Page<Review> findAllByTypeOrderByDesc(String 후기, PageRequest pageRequest);

//    Page<Review> findAllByTypeOrderByIdDesc(String 후기, PageRequest pageRequest);


    // Main Forums
    @Query("SELECT f FROM Forum f WHERE f.isTemporary = false")
    Page<Forum> findAllWithoutTemp(Pageable pageable);



}
