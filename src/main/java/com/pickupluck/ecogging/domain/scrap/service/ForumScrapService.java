package com.pickupluck.ecogging.domain.scrap.service;

import com.pickupluck.ecogging.domain.scrap.dto.MyForumScrapsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Map;


public interface ForumScrapService {
  
    Boolean setForumScrap(Long forumId, Long userId) throws Exception;
    Boolean isForumScrap(Long forumId, Long userId) throws Exception;

  
  
    // MyPage 나의 커뮤니티 - 스크랩 ---------------------------------------------
    Map<String, Object> getMyForumScrapsAll(Long userId, Pageable pageable);
}
