package com.pickupluck.ecogging.domain.forum.service;

import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.util.PageInfo;

import java.util.List;
import java.util.Map;

public interface ForumService{
    List<ReviewDTO> getReviews(Integer page, PageInfo pageInfo) throws Exception;
    Map<String,Object> getMyForumList(Long userId, Integer page, String order) throws Exception;
}
