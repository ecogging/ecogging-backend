package com.pickupluck.ecogging.domain;

import com.pickupluck.ecogging.domain.plogging.dto.EventDTO;
import com.pickupluck.ecogging.domain.plogging.dto.MainEventResponseDto;
import com.pickupluck.ecogging.domain.plogging.service.AccompanyService;
import com.pickupluck.ecogging.domain.plogging.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MainController {

    @Autowired
    private AccompanyService accompanyService;
    @Autowired
    private EventService eventService;

    // MainAccompanies
    @GetMapping("/main/accompanies")
    public Map<String,Object> getAccompList() {
        try {
            Map <String, Object> map = accompanyService.getMainAccompanyList();
            return map;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Main Events
    @GetMapping("/main/events")
    public ResponseEntity<Map<String,Object>> getEventList(
            @PageableDefault(size = 4, sort = "createdAt", direction = Sort.Direction.DESC) final Pageable pageable) {

            // DB에서 최신순 4개 글 확보
            Page<MainEventResponseDto> mainEvents = eventService.getMainEvents(pageable);
            if (mainEvents.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("msg", "행사글 조회 완료~");
            responseBody.put("data", mainEvents.getContent());

            return ResponseEntity.ok(responseBody);
    }

}
