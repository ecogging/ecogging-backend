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

    Page<Forum> findByIsTemporaryFalseAndTypeAndWriterId(String type, Long userId, PageRequest pageRequest);

    Page<Forum> findByType(String type, Pageable pageable);


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

