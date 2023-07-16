package com.pickupluck.ecogging.domain.plogging.service;

import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.util.PageInfo;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getReviews(Integer page, PageInfo pageInfo) throws Exception;

    ReviewDTO getReviewInfo(Long id) throws Exception;
}
