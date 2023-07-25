package com.pickupluck.ecogging.domain.scrap.api;

import com.pickupluck.ecogging.domain.scrap.dto.EventScrapDTO;
import com.pickupluck.ecogging.domain.scrap.service.EventScrapService;
import com.pickupluck.ecogging.util.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EventScrapController {

    private final EventScrapService eventScrapService;

    @GetMapping("/eventScrapList/{page}/{sorttype}")
    public ResponseEntity<Map<String,Object>> eventScrapList(@PathVariable Integer page, @PathVariable String sorttype) {
        try {
            PageInfo pageInfo = new PageInfo();
            List<EventScrapDTO> list = eventScrapService.getEventScrapList(page, pageInfo, sorttype);
            // 현재 페이지가 마지막 페이지인 경우 응답하지 않음
            if (list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            boolean isLastPage = page >= pageInfo.getAllPage(); // 현재 페이지가 마지막 페이지인지 여부 판단
            Map<String, Object> res = new HashMap<>();
            res.put("pageInfo", pageInfo);
            res.put("list", list);
            res.put("isLastPage", isLastPage); // 현재 페이지가 마지막 페이지인지 여부 전달
            return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
        }
    }
}
