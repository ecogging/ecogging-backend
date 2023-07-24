package com.pickupluck.ecogging.domain.forum.api;

import com.pickupluck.ecogging.domain.forum.dto.ForumDTO;
import com.pickupluck.ecogging.domain.forum.service.RouteService;
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
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/routes/{page}")
    public ResponseEntity<Map<String,Object>> routes(@PathVariable Integer page){

        System.out.println("루트 목록");
        System.out.println("page : "+page);
        System.out.println("routes test");
        try {
            PageInfo pageInfo=new PageInfo();
            List<ForumDTO> routes=routeService.getRoutes(page, pageInfo);

            Map<String,Object> res=new HashMap<>();
            res.put("pageInfo",pageInfo);
            res.put("routes",routes);
            for(ForumDTO a: routes){
                System.out.println(a);
            }

            return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
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

    @PostMapping("/routeWrite")
    public ResponseEntity<String> routeWrite(@RequestBody Map<String, String> requestData){
        String content=requestData.get("content");
        String title=requestData.get("title");
        String routeLocation=requestData.get("route_location");

        try {
            System.out.println("루틴 작성 컨트롤러");
            Map<String,String> res=new HashMap<>();
            res.put("title",title);
            res.put("content",content);
            res.put("routeLocation",routeLocation);
            routeService.routeWrite(res);
            return new ResponseEntity<>("controller message : 루틴 등록 성공",HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("controller message : 루틴 등록 실패",HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping ("/routeInfo")
    public ResponseEntity<Map<String,Object>> routeInfo(@RequestBody Map<String, Long> param){

        try {
            Map<String,Object> map=new HashMap<>();
            ForumDTO routeInfo=routeService.getRouteInfo(param.get("id"));
            map.put("routeInfo",routeInfo);
            return new ResponseEntity<>(map,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
