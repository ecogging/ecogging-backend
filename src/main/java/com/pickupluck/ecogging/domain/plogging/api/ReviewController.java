package com.pickupluck.ecogging.domain.plogging.api;

import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.domain.forum.service.ForumService;
import com.pickupluck.ecogging.util.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    @Autowired
    private ForumService forumService;


    @GetMapping("/reviews/{page}")
    public ResponseEntity<Map<String,Object>> reviews(@PathVariable Integer page){

        System.out.println("page : "+page);
        System.out.println("reviews test");
        try {
            PageInfo pageInfo=new PageInfo();
            List<ReviewDTO> reviews=forumService.getReviews(page, pageInfo);

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

        try {
            Map<String,Object> map=new HashMap<>();
            ReviewDTO reviewInfo=forumService.getReviewInfo(param.get("id"));
            map.put("reviewInfo",reviewInfo);
            return new ResponseEntity<>(map,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping ("/reviewModify/{id}")
    public ResponseEntity<String> reviewModify(@RequestBody Map<String, String> requestData,@PathVariable long id){
        System.out.println("리뷰 수정하기");
        System.out.println("id : "+id);
        String content=requestData.get("content");
        String title=requestData.get("title");

        try {
            Map<String,String> res=new HashMap<>();
            res.put("title",title);
            res.put("content",content);
            forumService.reviewModify(res,id);
            return new ResponseEntity<>("리뷰 수정 완료",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("리뷰 수정 실패",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reviewImgUpload")
    public ResponseEntity<String> reviewImgUpload(MultipartFile file) {
        System.out.println("file : "+file);
        try {
            System.out.println("리뷰 작성");
            if(file.isEmpty()){
                System.out.println("비어있음");
                return new ResponseEntity<>("controller message : 파일이 비어있습니다.", HttpStatus.BAD_REQUEST);
            }
            String imgfile=forumService.reviewImgUpload(file);


            return new ResponseEntity<String>(imgfile,HttpStatus.OK);
        }catch (Exception e){
            System.out.println("리뷰이미지등록실패 "+e.getMessage());
            return new ResponseEntity<>("controller message : 리뷰 이미지 등록 실패",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reviewWrite")
    public ResponseEntity<String> reviewWrite(@RequestBody Map<String, String> requestData){
        String content=requestData.get("content");
        String title=requestData.get("title");

        try {
            System.out.println("review Write Controller");
            Map<String,String> res=new HashMap<>();
            res.put("title",title);
            res.put("content",content);
            forumService.reviewWrite(res);
            return new ResponseEntity<>("controller message : 리뷰 등록 성공",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("controller message : 리뷰 등록 실패",HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/reviewDel/{id}")
    public ResponseEntity<String> reviewDel(@PathVariable long id){
        System.out.println("id : "+id);
        try {
            System.out.println("review del Controller");
            forumService.reviewDel(id);
            return new ResponseEntity<>("controller message : 리뷰 등록 성공",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("controller message : 리뷰 등록 실패",HttpStatus.BAD_REQUEST);
        }
    }
}
