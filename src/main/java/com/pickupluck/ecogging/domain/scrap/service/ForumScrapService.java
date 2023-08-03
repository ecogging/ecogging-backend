package com.pickupluck.ecogging.domain.scrap.service;

public interface ForumScrapService {
    Boolean setForumScrap(Long forumId, Long userId) throws Exception;
    Boolean isForumScrap(Long forumId, Long userId) throws Exception;
}
