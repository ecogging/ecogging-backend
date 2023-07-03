package com.pickupluck.ecogging.domain.forum.service;

import com.pickupluck.ecogging.domain.forum.dto.ForumDto;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.util.PageInfo;

import java.util.List;

public interface ForumService{
    List<ForumDto> getReviews(Integer page, PageInfo pageInfo) throws Exception;
//    List<Forum> getReviewsTest() throws Exception;
}
