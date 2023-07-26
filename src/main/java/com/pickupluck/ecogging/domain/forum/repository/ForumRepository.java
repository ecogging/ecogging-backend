package com.pickupluck.ecogging.domain.forum.repository;

import com.pickupluck.ecogging.domain.forum.entity.Forum;
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

    //@Query ("SELECT r FROM Forum r WHERE r.isTemporary = false AND r.type = '후기' AND r.writer.id = :userId")
    Page<Forum> findByIsTemporaryFalseAndTypeAndWriterId(String type, Long userId, PageRequest pageRequest);

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


    // Main Forums -----------------------------------------------------------------------------------
    @Query("SELECT f FROM Forum f WHERE f.isTemporary = false AND (f.type = '경로' OR f.type = '나눔')")
    Page<Forum> findAllWithoutTemp(Pageable pageable);

    // My Forums -------------------------------------------------------------------------------------
    @Query("SELECT s FROM Forum s WHERE s.isTemporary = false AND s.type = :type AND s.writer.id = :userId")
    Page<Forum> findAllByUserIdAndType(@Param("userId")Long userId, Pageable pageable, @Param("type")String type);

    // ** 페이징 위한 데이터 전체 개수 구하기 위한 리스트
    @Query("SELECT s FROM Forum s WHERE s.isTemporary = false AND s.type = :type AND s.writer.id = :userId")
    List<Forum> findAllByUserIdAndType(@Param("userId")Long userId, @Param("type")String type);
}

