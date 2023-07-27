package com.pickupluck.ecogging.domain.forum.api;
import com.pickupluck.ecogging.domain.forum.dto.ForumDTO;
import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.forum.service.ForumService;

import com.pickupluck.ecogging.util.PageInfo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public class ForumController {

    @Autowired
    private ForumService forumService;




    // RouteController ---------------------------------------------------------------------------------
    @GetMapping("/routes/{pageNo}")
    public ResponseEntity<Map<String,Object>> routes(@PathVariable Integer pageNo) {
        System.out.println("루트 목록");
        pageNo = pageNo==0 ? 0 : (pageNo-1); // -> 프론트: 1부터 시작 BUT Page: 0부터 시작 -> Page에 맞춰주기
        Pageable pageable = PageRequest.of(pageNo, 5, Sort.by("createdAt").descending()); // Pageable 객체 조건 맞춰 생성


        try {
            Map<String, Object> routes=forumService.getRoutes(pageable);

//            for(ForumDTO a: routes){
//                System.out.println("루트 테스트 : "+a.getContent());
//            }

            return new ResponseEntity<Map<String,Object>>(routes, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("shares error");
            return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
        }


    }



//    @PostMapping("/shareImgUpload")
//    public ResponseEntity<String> shareImgUpload(MultipartFile file) {
//        System.out.println("file : "+file);
//        try {
//            System.out.println("나눔 작성");
//            if(file.isEmpty()){
//                System.out.println("비어있음");
//                return new ResponseEntity<>("controller message : 파일이 비어있습니다.", HttpStatus.BAD_REQUEST);
//            }
//            String imgfile=shareService.shareImgUpload(file);
//
//
//            return new ResponseEntity<String>(imgfile,HttpStatus.OK);
//        }catch (Exception e){
//            System.out.println("나눔이미지등록실패 "+e.getMessage());
//            return new ResponseEntity<>("controller message : 나눔 이미지 등록 실패",HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("/routeWrite/{userId}/{temp}")
    public ResponseEntity<String> routeWrite(@RequestBody Map<String, String> requestData, @PathVariable Long userId, @PathVariable Boolean temp){
        String content=requestData.get("content");
        String title=requestData.get("title");
        String routeLocation=requestData.get("route_location");

        try {
            System.out.println("루틴 작성 컨트롤러");
            Map<String,String> res=new HashMap<>();
            res.put("title",title);
            res.put("content",content);
            res.put("routeLocation",routeLocation);
            forumService.routeWrite(res,userId,temp);
            return new ResponseEntity<>("controller message : 루틴 등록 성공",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("controller message : 루틴 등록 실패",HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("/routeModify/{userId}/{forumId}/{temp}")
    public ResponseEntity<String> routeModify(@RequestBody ForumDTO forumDTO, @PathVariable Long userId, @PathVariable Long forumId, @PathVariable Boolean temp){
//        String content=requestData.get("content");
//        String title=requestData.get("title");
//        String routeLocation=requestData.get("route_location");

        try {
            System.out.println("루틴 작성 컨트롤러");
//            Map<String,String> res=new HashMap<>();
//            res.put("title",title);
//            res.put("content",content);
//            res.put("routeLocation",routeLocation);
            forumService.routeModify(forumDTO,userId,forumId,temp);
            return new ResponseEntity<>("controller message : 루틴 등록 성공",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("controller message : 루틴 등록 실패",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping ("/routeInfo/{id}/{userId}")
    public ResponseEntity<Map<String,Object>> routeInfo(@PathVariable Long id,@PathVariable Long userId){

        try {
            Map<String,Object> map=new HashMap<>();
            ForumDTO routeInfo=forumService.getRouteInfo(id);
            map.put("routeInfo",routeInfo);
            map.put("isScrap", forumService.isForumScrap(id,userId));
            System.out.println("루트 인포포오ㅗㅇ : "+routeInfo.getContent());
            return new ResponseEntity<>(map,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }







    // ShareController ---------------------------------------------------------------------------------------
    @GetMapping("/shares/{pageNo}")
    public ResponseEntity<Map<String,Object>> shares(@PathVariable Integer pageNo) {
        System.out.println("루트 목록");
        pageNo = pageNo==0 ? 0 : (pageNo-1); // -> 프론트: 1부터 시작 BUT Page: 0부터 시작 -> Page에 맞춰주기
        Pageable pageable = PageRequest.of(pageNo, 5, Sort.by("createdAt").descending()); // Pageable 객체 조건 맞춰 생성


        try {
            Map<String, Object> shares=forumService.getShares(pageable);

//            for(ForumDTO a: routes){
//                System.out.println("루트 테스트 : "+a.getContent());
//            }

            return new ResponseEntity<Map<String,Object>>(shares, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("shares error");
            return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
        }


    }


    @PostMapping("/shareImgUpload")
    public ResponseEntity<String> shareImgUpload(MultipartFile file) {
        System.out.println("file : "+file);
        try {
            System.out.println("나눔 작성");
            if(file.isEmpty()){
                System.out.println("비어있음");
                return new ResponseEntity<>("controller message : 파일이 비어있습니다.", HttpStatus.BAD_REQUEST);
            }
            String imgfile=forumService.shareImgUpload(file);


            return new ResponseEntity<String>(imgfile,HttpStatus.OK);
        }catch (Exception e){
            System.out.println("나눔이미지등록실패 "+e.getMessage());
            return new ResponseEntity<>("controller message : 나눔 이미지 등록 실패",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/shareWrite/{userId}/{temp}")
    public ResponseEntity<String> sharewWrite(@PathVariable Long userId,@PathVariable Boolean temp, @RequestBody Map<String, String> requestData){
        String content=requestData.get("content");
        String title=requestData.get("title");
        System.out.println("userId : "+userId);

        try {
            System.out.println("나눔 작성 컨트롤러");
            Map<String,String> res=new HashMap<>();
            res.put("title",title);
            res.put("content",content);
            forumService.shareWrite(res,userId,temp);
            return new ResponseEntity<>("controller message : 나눔 등록 성공",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("controller message : 나눔 등록 실패",HttpStatus.BAD_REQUEST);
        }


    }
    @PostMapping ("/shareInfo/{id}/{userId}")
    public ResponseEntity<Map<String,Object>> shareInfo(@PathVariable Long id, @PathVariable Long userId){
        System.out.println("나눔 컨트롤러어러러어러얼");
        try {
            Map<String,Object> map=new HashMap<>();
            ForumDTO shareInfo=forumService.getShareInfo(id);
            map.put("shareInfo",shareInfo);
            map.put("isScrap", forumService.isForumScrap(id,userId));
            return new ResponseEntity<>(map,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/shareDel/{id}")
    public ResponseEntity<String> shareDel(@PathVariable long id){
        System.out.println("id : "+id);
        try {
            System.out.println("share del Controller");
            forumService.shareDel(id);
            return new ResponseEntity<>("controller message : 나눔 등록 성공",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("controller message : 나눔 등록 실패",HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping ("/shareModify/{id}")
    public ResponseEntity<String> shareModify(@RequestBody Map<String, String> requestData,@PathVariable Long id){
        System.out.println("나눔 수정하기");
        System.out.println("id : "+id);
        String content=requestData.get("content");
        String title=requestData.get("title");

        try {
            Map<String,String> res=new HashMap<>();
            res.put("title",title);
            res.put("content",content);
            forumService.shareModify(res,id);
            return new ResponseEntity<>("나눔 수정 완료",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("나눔 수정 실패",HttpStatus.BAD_REQUEST);
        }
    }

    //forum scrap
    @PostMapping("/forumscrap/{forumId}/{userId}")
    public ResponseEntity<Boolean> forumScrap(@PathVariable Long forumId, @PathVariable Long userId){

        try {
            System.out.println("나눔 스크랩 컨트롤러 들어오기 성고옹");
            Boolean isScrap = forumService.setForumScrap(forumId,userId);
            return new ResponseEntity<>(isScrap,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping ("/myforumscrapInfo/{userId}")
    public ResponseEntity<List<Forum>> myforumscrapInfo(@PathVariable Long userId) {
        try {
            System.out.println("마이페이지 스크랩 처리");
            List<Forum> list=forumService.getMyforumScrap(userId);
//            Map<String,Object> map=forumService.getMyforumScrap(userId);
            return new ResponseEntity<>(list,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
