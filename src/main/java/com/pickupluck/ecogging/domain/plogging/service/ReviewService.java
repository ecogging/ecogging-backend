package com.pickupluck.ecogging.domain.plogging.service;

import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.util.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ReviewService {
    List<ReviewDTO> getReviews(Integer page, PageInfo pageInfo) throws Exception;

    ReviewDTO getReviewInfo(Long id) throws Exception;

    void modifyReviewInfo(ReviewDTO reviewDTO) throws Exception;

    String reviewImgUpload(MultipartFile file) throws Exception;

    void reviewWrite(Map<String, String> res) throws Exception;

    void reviewModify(Map<String, String> res, long id) throws Exception;

    void reviewDel(long id) throws Exception;
}
