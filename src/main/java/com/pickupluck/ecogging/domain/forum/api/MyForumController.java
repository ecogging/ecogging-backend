package com.pickupluck.ecogging.domain.forum.api;

import com.pickupluck.ecogging.domain.forum.dto.MyForumRouteResponseDto;
import com.pickupluck.ecogging.domain.forum.dto.MyForumShareResponseDto;
import com.pickupluck.ecogging.domain.forum.repository.ForumRepository;
import com.pickupluck.ecogging.domain.forum.service.ForumService;
import com.pickupluck.ecogging.domain.scrap.dto.MyForumScrapsResponseDto;
import com.pickupluck.ecogging.domain.scrap.service.ForumScrapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyForumController {

    private final ForumService forumService;
    private final ForumRepository forumRepository;

    private final ForumScrapService forumScrapService;

    // 내가 작성한 나눔
    @GetMapping("/mypage/{userId}/shares")
    public ResponseEntity<Map<String, Object>> getMyShares(@PathVariable("userId") Long userId,
                                                           @RequestParam("pageNo") int pageNo) {

        pageNo = pageNo==0 ? 0 : (pageNo-1); // -> 프론트: 1부터 시작 BUT Page: 0부터 시작 -> Page에 맞춰주기
        Pageable pageable = PageRequest.of(pageNo, 5, Sort.by("createdAt").descending()); // Pageable 객체 조건 맞춰 생성

        // DB에서 최신순 5개 글 , 전체 데이터 개수 확보
        Map<String, Object> myShareMap = forumService.getMyShares(userId, pageable);
        Page<MyForumShareResponseDto> myShares = (Page<MyForumShareResponseDto>)myShareMap.get("res");
        if (myShares.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        int all = (int)myShareMap.get("all");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("msg", "MYFORUM 나눔 조회 완료");
        responseBody.put("data", myShares.getContent());
        responseBody.put("allCount", all); // 전체 데이터 개수 같이 넘겨주기

        return ResponseEntity.ok(responseBody);
    }

    // 내가 작성한 경로추천
    @GetMapping("/mypage/{userId}/recommendations")
    public ResponseEntity<Map<String, Object>> getMyRoutes(@PathVariable("userId") Long userId,
                                                           @RequestParam("pageNo") int pageNo
    ) {

        pageNo = pageNo==0 ? 0 : (pageNo-1); // -> 프론트: 1부터 시작 BUT Page: 0부터 시작 -> Page에 맞춰주기
        Pageable pageable = PageRequest.of(pageNo, 5, Sort.by("createdAt").descending()); // Pageable 객체 조건 맞춰 생성

        // DB에서 최신순 5개 글, 전체 데이터 개수 확보
        Map<String, Object> myRouteMap = forumService.getMyRoutes(userId, pageable);
        Page<MyForumRouteResponseDto> myRoutes = ( Page<MyForumRouteResponseDto> )myRouteMap.get("res");
        if (myRoutes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        int all = (int)myRouteMap.get("all");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("msg", "MYFORUM 경로추천 조회 완료");
        responseBody.put("data", myRoutes.getContent());
        responseBody.put("allCount", all); // 전체 데이터 개수 같이 넘겨주기

        return ResponseEntity.ok(responseBody);
    }



    // 내가 스크랩한 경로와 나눔 (커뮤니티)
    @GetMapping("/mypage/{userId}/forumscraps")
    public ResponseEntity<Map<String, Object>> getMyForumScraps(@PathVariable("userId") Long userId,
                                                                @RequestParam("pageNo") int pageNo) {
        // pagination
        pageNo = pageNo==0 ? 0 : (pageNo-1);
        Pageable pageable = PageRequest.of(pageNo, 5, Sort.by("createdAt").descending());

        // get data from DB
        Map<String, Object> myForumScrapsMap = forumScrapService.getMyForumScrapsAll(userId, pageable);
        Page<MyForumScrapsResponseDto> myForumScraps = ( Page<MyForumScrapsResponseDto> )myForumScrapsMap.get("res");
        if (myForumScraps.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Long all = (Long) myForumScrapsMap.get("all");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("msg", "MYFORUM 스크랩한 글 조회 완료");
        responseBody.put("data", myForumScraps.getContent());
        responseBody.put("allCount", all); // 전체 데이터 개수 같이 넘겨주기

        return ResponseEntity.ok(responseBody);
    }

    // 내가 스크랩한 경로와 나눔 검색
    @GetMapping("/mypage/{userId}/forumscraps/search")
    public ResponseEntity<Map<String, Object>> getMyForumScrapsSearch(@PathVariable("userId") Long userId,
                                                                      @RequestParam("pageNo") int pageNo,
                                                                      @RequestParam("searchCriteria") String criteria,
                                                                      @RequestParam("searchWords") String words) {
        // pagination
        pageNo = pageNo==0 ? 0 : (pageNo-1);
        Pageable pageable = PageRequest.of(pageNo, 5, Sort.by("createdAt").descending());

        // get data from DB
        Map<String, Object> myForumScrapSearchMap = forumScrapService.searchMyForumScraps(userId, criteria, words, pageable);
        Page<MyForumScrapsResponseDto> myForumScraps = ( Page<MyForumScrapsResponseDto> )myForumScrapSearchMap.get("res");
        if (myForumScraps.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Long all = (Long) myForumScrapSearchMap.get("all");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("msg", "MYFORUMSCRAP 검색 조회 완료");
        responseBody.put("data", myForumScraps.getContent());
        responseBody.put("allCount", all); // 전체 데이터 개수 같이 넘겨주기

        return ResponseEntity.ok(responseBody);
    }

}
