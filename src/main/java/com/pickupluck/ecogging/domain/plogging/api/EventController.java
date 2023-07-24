package com.pickupluck.ecogging.domain.plogging.api;

import com.pickupluck.ecogging.domain.plogging.dto.EventDTO;
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


    @GetMapping("/eventList/{page}/{sorttype}")
    public ResponseEntity<Map<String,Object>> eventList(@PathVariable Integer page, @PathVariable String sorttype) {
        try {
            PageInfo pageInfo = new PageInfo();
            List<EventDTO> list = eventService.getEventList(page, pageInfo, sorttype);
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
    @PostMapping("/myevent")
    public ResponseEntity<Map<String,Object>> myEventList(@RequestBody Map<String,Object> param) {
        Long userId = Long.parseLong((String) param.get("userId"));
        Integer page = Integer.valueOf((String)param.get("page"));
        System.out.println(page);
        try {
            Map<String,Object> map= eventService.getMyEventList(userId, page);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/myevnettemp")
    public ResponseEntity<Map<String,Object>> myEvnetTempList(@RequestBody Map<String,Object> param) {
        Long userId = Long.parseLong((String) param.get("userId"));
        Integer page = Integer.valueOf((String)param.get("page"));
        try {
            Map<String,Object> map= eventService.getMyEventTempList(userId, page);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/myaeventscrap")
    public ResponseEntity<Map<String,Object>> myEventscrapList(@RequestBody Map<String,Object> param) {
        Long userId = Long.parseLong((String) param.get("userId"));
        Integer page = Integer.valueOf((String)param.get("page"));
        try {
            Map<String,Object> map= eventService.getMyEventscrapList(userId, page);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/eventDetail")
    public ResponseEntity<Map<String,Object>> eventDetail(@RequestBody Map<String, Integer> param) {
        ResponseEntity<Map<String,Object>> res = null;
        try {
            Map<String,Object> map = new HashMap<>();
            EventDTO eventDTO = eventService.getEvent(param.get("eventId"));
            map.put("event", eventDTO);
            if(param.get("userId") != null) {
                Boolean isEventscrap = eventService.isEventScrap(Long.valueOf(param.get("userId")), param.get("eventId"));
                map.put("isEventscrap", isEventscrap);
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

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

        @PostMapping("/eventModify")
        public ResponseEntity<String> eventModify(@ModelAttribute EventDTO eventDTO, MultipartFile file) {
           // ResponseEntity<String> res = null;
            try {
                eventService.modifyEvent(eventDTO, file);
                return new ResponseEntity<String>("동행모집 등록", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
            }
        }

        @DeleteMapping("/eventDelete/{eventId}")
        public  ResponseEntity<Boolean> eventDelete(@PathVariable Integer eventId) {
            ResponseEntity<Boolean> res = null;
            try {
                eventService.removeEvent(eventId);
                return new ResponseEntity<>(true, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

        @PostMapping("/eventScrap")
        public ResponseEntity<Boolean> eventScrap(@RequestBody Map<String,Long> param) {
            ResponseEntity<Boolean> res = null;
            try {
                Boolean isScrap = eventService.toggleEventScrap(param.get("userId"), param.get("eventId"));
                return new ResponseEntity<>(isScrap, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

}

