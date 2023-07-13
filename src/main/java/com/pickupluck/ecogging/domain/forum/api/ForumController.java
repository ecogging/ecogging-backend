package com.pickupluck.ecogging.domain.forum.api;

import com.pickupluck.ecogging.domain.forum.dto.ForumDto;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.forum.service.ForumService;
import com.pickupluck.ecogging.util.PageInfo;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ForumController {

    @Autowired
    private ForumService reviewService;


    @GetMapping("/reviews/{page}")
    public ResponseEntity<Map<String,Object>> reviews(@PathVariable Integer page){

        System.out.println("page : "+page);
        try {
            PageInfo pageInfo=new PageInfo();
            List<ForumDto> reviews=reviewService.getReviews(page, pageInfo);
            Map<String,Object> res=new HashMap<>();
            res.put("pageInfo",pageInfo);
            res.put("reviews",reviews);
            return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
        }
    }
}
