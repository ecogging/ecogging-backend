package com.pickupluck.ecogging.domain.forum.service;

import com.pickupluck.ecogging.domain.forum.dto.ForumDTO;
import com.pickupluck.ecogging.domain.forum.dto.MainForumsResponseDto;
import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.util.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ForumService{
    List<ReviewDTO> getReviews(Integer page, PageInfo pageInfo) throws Exception;


    // Main Forums
    Page<MainForumsResponseDto> getMainForums(Pageable pageable);
}
