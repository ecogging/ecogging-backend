package com.pickupluck.ecogging.domain.plogging.api;

import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.domain.forum.service.ForumService;
import com.pickupluck.ecogging.domain.plogging.entity.Review;
import com.pickupluck.ecogging.domain.plogging.service.ReviewService;
import com.pickupluck.ecogging.util.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    @Autowired
    private ReviewService reviewService;


    @GetMapping("/reviews/{page}")
    public ResponseEntity<Map<String,Object>> reviews(@PathVariable Integer page){

        System.out.println("page : "+page);
        System.out.println("reviews test");
        try {
            PageInfo pageInfo=new PageInfo();
            List<ReviewDTO> reviews=reviewService.getReviews(page, pageInfo);

            Map<String,Object> res=new HashMap<>();
            res.put("pageInfo",pageInfo);
            res.put("reviews",reviews);
            for(ReviewDTO a: reviews){
                System.out.println(a);
            }

            return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("reviews error");
            return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping ("/reviewInfo")
    public ResponseEntity<Map<String,Object>> reviewInfo(@RequestBody Map<String, Long> param){
        ResponseEntity<Map<String,Object>> res=null;

        try {
            Map<String,Object> map=new HashMap<>();
            ReviewDTO reviewInfo=reviewService.getReviewInfo(param.get("id"));
            map.put("reviewInfo",reviewInfo);
            return new ResponseEntity<>(map,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping ("/reviewModify")
    public ResponseEntity<Map<String,Object>> reviewModify(@RequestBody Map<String, Long> param){
        return null;
    }

}
