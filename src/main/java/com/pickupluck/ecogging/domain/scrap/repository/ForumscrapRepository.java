package com.pickupluck.ecogging.domain.scrap.repository;

import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.scrap.entity.ForumScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ForumscrapRepository extends JpaRepository<ForumScrap, Long> {

    Optional<ForumScrap>  findByForumIdAndUserId(Long forumId, Long userId);
}
