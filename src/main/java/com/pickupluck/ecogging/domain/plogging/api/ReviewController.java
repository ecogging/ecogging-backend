package com.pickupluck.ecogging.domain.plogging.api;

import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.plogging.dto.ReviewDTO;
import com.pickupluck.ecogging.domain.forum.service.ForumService;
import com.pickupluck.ecogging.util.PageInfo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    @Autowired
    private ForumService forumService;


    @GetMapping("/reviews/{page}/{userId}")
    public ResponseEntity<Map<String,Object>> reviews(@PathVariable Long userId,
                                                      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) final Pageable pageable){

//        System.out.println("page : "+page);
        System.out.println("reviews test");
        try {
            PageInfo pageInfo=new PageInfo();
            Page<ReviewDTO> reviews=forumService.getReviews(userId, pageable);

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

    @PostMapping ("/reviewInfo/{id}/{userId}")
    public ResponseEntity<Map<String,Object>> reviewInfo(@PathVariable Long id,@PathVariable Long userId){

        try {
            Map<String,Object> map=new HashMap<>();
            ReviewDTO reviewInfo=forumService.getReviewInfo(id,userId);
            map.put("reviewInfo",reviewInfo);
            System.out.println("후기 인포 테스트 : "+reviewInfo.getContent());
            map.put("isScrap", forumService.isForumScrap(id,userId));
            return new ResponseEntity<>(map,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping ("/reviewModify/{id}/{userId}")
    public ResponseEntity<String> reviewModify(@RequestBody Map<String, String> requestData,@PathVariable Long id, @PathVariable Long userId){
        System.out.println("리뷰 수정하기");
        System.out.println("id : "+id);
        String content=requestData.get("content");
        String title=requestData.get("title");

        try {
            Map<String,String> res=new HashMap<>();
            res.put("title",title);
            res.put("content",content);
            forumService.reviewModify(res,id,userId);
            return new ResponseEntity<>("리뷰 수정 완료",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("리뷰 수정 실패",HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/imageview/{filename}")
    public void imageView(@PathVariable String filename, HttpServletResponse response) {
        try {
            FileInputStream fis = new FileInputStream("C:/JSR/front-work/upload/" + filename);
            OutputStream out = response.getOutputStream();
            FileCopyUtils.copy(fis, out);
            out.flush();
            fis.close();
        } catch(Exception e) {
            e.printStackTrace();
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

    @PostMapping("/reviewWrite/{accompanyId}/{temp}/{userId}")
    public ResponseEntity<String> reviewWrite(@PathVariable Long accompanyId,@PathVariable Boolean temp ,@PathVariable Long userId, @RequestBody Map<String, String> requestData){

        System.out.println("id : "+accompanyId);
        System.out.println("temp : "+temp);
        System.out.println("userId : "+userId);
        String content=requestData.get("content");
        String title=requestData.get("title");
        System.out.println("content : "+content);
        System.out.println("title : "+title);

        try {
            System.out.println("!!!!!!!review Write Controller!!!!!!");
            Map<String,String> res=new HashMap<>();
            res.put("title",title);
            res.put("content",content);
            forumService.reviewWrite(res,accompanyId,temp,userId);
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
