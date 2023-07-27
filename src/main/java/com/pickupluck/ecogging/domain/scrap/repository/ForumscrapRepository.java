package com.pickupluck.ecogging.domain.scrap.repository;

import com.pickupluck.ecogging.domain.forum.dto.ForumDTO;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ForumscrapRepository extends JpaRepository<Scrap, Long> {

    Optional<Scrap>  findByForumIdAndUserId(Long forumId, Long userId);

    @Query("select fs.forum from Scrap fs where fs.userId=:userId")
    List<Forum> findAllByUserId(@Param("userId")Long userId);
}
