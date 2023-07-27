package com.pickupluck.ecogging.domain.forum.service;

import com.pickupluck.ecogging.domain.forum.dto.ForumDTO;
import com.pickupluck.ecogging.domain.forum.dto.MainForumsResponseDto;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.util.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ForumService {
//    List<ReviewDTO> getReviews(Long page, Pageable pageInfo) throws Exception;

    Map<String,Object> getMyForumList(Long userId, Integer page, String order) throws Exception;

    // RouteService ----------------------------------------------------------
//    List<ForumDTO> getRoutes(Integer page, PageInfo pageInfo) throws Exception;
    void routeWrite(Map<String, String> res, Long userId, Boolean temp) throws Exception;
    ForumDTO getRouteInfo(Long id) throws Exception;


    // ShareService ----------------------------------------------------------
    Page<ForumDTO> getShares(Long userId, Pageable pageable) throws Exception;
//    void shareWrite(Map<String, String> res) throws Exception;
    String shareImgUpload(MultipartFile file) throws Exception;
    ForumDTO getShareInfo(Long id) throws Exception;
    void shareDel(long id) throws Exception;
    void shareModify(Map<String, String> res, Long id) throws Exception;


    // ReviewService ----------------------------------------------------------
    List<ReviewDTO> getReviewsRv(Integer page, PageInfo pageInfo) throws Exception;

    ReviewDTO getReviewInfo(Long id, Long userId) throws Exception;

    void modifyReviewInfo(ReviewDTO reviewDTO) throws Exception;

    String reviewImgUpload(MultipartFile file) throws Exception;

    void reviewWrite(Map<String, String> res, Long id, Boolean temp, Long userId) throws Exception;

    void reviewModify(Map<String, String> res, Long id, Long userId) throws Exception;

    void reviewDel(long id) throws Exception;


    // Main Forums ------------------------------------------------------------
    Page<MainForumsResponseDto> getMainForums(Pageable pageable);

    // share
    void shareWrite(Map<String, String> res, Long userId, Boolean temp) throws Exception;

    //routes
    Page<ForumDTO> getRoutes(Long userId, Pageable pageable) throws Exception;

    //revivews
    Page<ReviewDTO> getReviews(Long userId, Pageable pageable) throws Exception;

    void routeModify(ForumDTO forumDTO, Long userId, Long id, Boolean temp) throws Exception;

    //share scrap
    Boolean setForumScrap(Long forumId, Long userId) throws Exception;
    Boolean isForumScrap(Long forumId, Long userId) throws Exception;

    // MyForum(SHARE, ROUTE)  --------------------------------------------------------
    Map<String, Object> getMyShares(Long userId, Pageable pageable);
//    Page<MyForumRouteResponseDto> getMyRoutes(Long userId, Pageable pageable);
    Map<String, Object> getMyRoutes(Long userId, Pageable pageable);

    Boolean myForumDelete(Long forumId) throws Exception;

    List<Forum> getMyforumScrap(Long userId) throws Exception;
}
