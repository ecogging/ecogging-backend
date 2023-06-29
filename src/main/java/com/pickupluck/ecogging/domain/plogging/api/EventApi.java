package com.pickupluck.ecogging.domain.plogging.api;

import com.pickupluck.ecogging.domain.plogging.dto.EventDTO;
import com.pickupluck.ecogging.domain.plogging.service.EventService;
import com.pickupluck.ecogging.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EventApi {

    @Autowired
    private EventService eventService;

    @GetMapping("/eventList/{page}")
    public ResponseEntity<Map<String,Object>> eventList(@PathVariable Integer page) {
        try {
            PageInfo pageInfo = new PageInfo();
            List<EventDTO> list = eventService.getEventList(page, pageInfo);
            Map<String, Object> res = new HashMap<>();
            res.put("pageInfo", pageInfo);
            res.put("list", list);
            return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
        }
    }
}
