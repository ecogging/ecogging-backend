package com.pickupluck.ecogging.domain.forum.api;

import com.pickupluck.ecogging.domain.forum.dto.MyForumShareResponseDto;
import com.pickupluck.ecogging.domain.forum.service.ForumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyForumController {

    private final ForumService forumService;

    @GetMapping("/mypage/{userId}/shares")
    public ResponseEntity<Map<String, Object>> getMyShares (@PathVariable("userId")Long userId,
                                                            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) final Pageable pageable) {
        // DB에서 최신순 5개 글 확보
        Page<MyForumShareResponseDto> myShares = forumService.getMyShares(userId, pageable);
        if(myShares.isEmpty()) {
            System.out.println("없음$$$$$$$$$$$$$$$$$$$$");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("msg", "MYFORUM 후기 조회 완료");
        responseBody.put("data", myShares.getContent());

        return ResponseEntity.ok(responseBody);
    }
}
