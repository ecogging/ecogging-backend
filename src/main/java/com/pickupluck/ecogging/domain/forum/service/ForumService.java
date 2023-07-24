package com.pickupluck.ecogging.domain.forum.service;

import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.util.PageInfo;

import java.util.List;

public interface ForumService{
    List<ReviewDTO> getReviews(Integer page, PageInfo pageInfo) throws Exception;
}
