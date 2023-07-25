package com.pickupluck.ecogging.domain.forum.service;

import com.pickupluck.ecogging.domain.forum.dto.ForumDTO;
import com.pickupluck.ecogging.domain.forum.dto.MainForumsResponseDto;
import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.util.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ForumService{
    List<ReviewDTO> getReviews(Integer page, PageInfo pageInfo) throws Exception;
<<<<<<< HEAD
    Map<String,Object> getMyForumList(Long userId, Integer page, String order) throws Exception;
=======

    // RouteService ----------------------------------------------------------
    List<ForumDTO> getRoutes(Integer page, PageInfo pageInfo) throws Exception;
    void routeWrite(Map<String, String> res) throws Exception;
    ForumDTO getRouteInfo(Long id) throws Exception;


    // ShareService ----------------------------------------------------------
    List<ForumDTO> getShares(Integer page, PageInfo pageInfo) throws Exception;
    void shareWrite(Map<String, String> res) throws Exception;
    String shareImgUpload(MultipartFile file) throws Exception;
    ForumDTO getShareInfo(Long id) throws Exception;
    void shareDel(long id) throws Exception;
    void shareModify(Map<String, String> res, long id) throws Exception;


    // ReviewService ----------------------------------------------------------
    List<ReviewDTO> getReviewsRv(Integer page, PageInfo pageInfo) throws Exception;

    ReviewDTO getReviewInfo(Long id) throws Exception;

    void modifyReviewInfo(ReviewDTO reviewDTO) throws Exception;

    String reviewImgUpload(MultipartFile file) throws Exception;

    void reviewWrite(Map<String, String> res) throws Exception;

    void reviewModify(Map<String, String> res, long id) throws Exception;

    void reviewDel(long id) throws Exception;


    // Main Forums ------------------------------------------------------------
    Page<MainForumsResponseDto> getMainForums(Pageable pageable);
>>>>>>> 25c153e7cbfa3a3d17b5b538615332e085fd56bc
}
