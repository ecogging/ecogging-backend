package com.pickupluck.ecogging.domain.scrap.repository;

import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.scrap.entity.ForumScrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ForumscrapRepository extends JpaRepository<ForumScrap, Long> {
  
    Optional<ForumScrap>  findByForumIdAndUserId(Long forumId, Long userId);



    // MyPage -----------------------------------------------------------------------
  
    // MyPage 나의 커뮤니티 - 스크랩 ( 모든 글 불러오기 )
    @Query("select s from ForumScrap s where s.user.id=:userId")
    Page<ForumScrap> findAllByUserId(@Param("userId") Long userId, Pageable pageable);
    // 스크랩한 모든 글 개수
    @Query("select count(*) from ForumScrap s where s.user.id=:userId")
    Long findAllByUserIdForCount(@Param("userId") Long userId);

    // 검색
    // 1. 전체일 경우
    @Query("select s from ForumScrap s where s.user.id=:userId and s.forum.title like %:words%")
    Page<ForumScrap> findByUserIdAndWords(@Param("userId") Long userId, @Param("words") String words, Pageable pageable);
    @Query("select count(*) from ForumScrap s where s.user.id=:userId and s.forum.title like %:words%")
    Long findByUserIdAndWordsForCount(@Param("userId") Long userId, @Param("words") String words);

    // 2. 경로 or 나눔일 경우
    @Query("select s from ForumScrap s where s.user.id=:userId and s.forum.type=:criteria and s.forum.title like %:words%")
    Page<ForumScrap> findByUserIdAndTypeAndWords(@Param("userId") Long userId, @Param("criteria") String criteria,
                                                 @Param("words") String words, Pageable pageable);
    @Query("select count(*) from ForumScrap s where s.user.id=:userId and s.forum.type=:criteria and s.forum.title like %:words%")
    Long findByUserIdAndTypeAndWordsForCount(@Param("userId") Long userId, @Param("criteria") String criteria,
                                            @Param("words") String words);
}
