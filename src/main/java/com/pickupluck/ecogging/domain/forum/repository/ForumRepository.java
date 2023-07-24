package com.pickupluck.ecogging.domain.forum.repository;

import com.pickupluck.ecogging.domain.forum.dto.MainForumsResponseDto;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum,Long> {

    // Main Forums
    @Query("SELECT f FROM Forum f WHERE f.isTemporary = false")
    Page<Forum> findAllWithoutTemp(Pageable pageable);
}
