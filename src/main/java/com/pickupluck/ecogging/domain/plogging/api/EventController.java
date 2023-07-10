package com.pickupluck.ecogging.domain.plogging.api;

import com.pickupluck.ecogging.domain.plogging.dto.EventDTO;
import com.pickupluck.ecogging.domain.plogging.entity.Event;
import com.pickupluck.ecogging.domain.plogging.service.EventService;
import com.pickupluck.ecogging.util.PageInfo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;


    @GetMapping("/eventList/{page}")
    public ResponseEntity<Map<String,Object>> eventList(@PathVariable Integer page) {
        try {
            PageInfo pageInfo = new PageInfo();
            List<EventDTO> list = eventService.getEventList(page, pageInfo);
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

    @GetMapping("/eventDetail/{eventId}")
    public  ResponseEntity<Event> eventDetail(@PathVariable Integer eventId) {
        try {
            Event event = eventService.getEvent(eventId);
            return new ResponseEntity<Event>(event, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Event>(HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/eventDetail/{eventId}")
//    public  ResponseEntity<Map<String,Object>> eventDetail(@PathVariable Integer eventId) {
//        try {
//            Map<String, Object> eventDetail = eventService.getEvent(eventId, request);
//            return new ResponseEntity<Map<String, Object>>(eventDetail, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
//        }
//    }

        @PostMapping("/eventWrite")
        public  ResponseEntity<String> eventWrite(@ModelAttribute EventDTO eventDTO, MultipartFile file) {
            try {
                eventService.writeEvent(eventDTO, file);
                return new ResponseEntity<String>("true",HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            }
        }

        @DeleteMapping("/eventDelete/{eventId}")
        public  ResponseEntity<Boolean> eventDelete(@PathVariable Integer eventId) {
            try {
                eventService.removeEvent(eventId);
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping("/eventImg/{fileId}")
        public  void eventImg(@PathVariable Long fileId, HttpServletResponse response) {
            try {
                eventService.readFile(fileId, response.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


}

