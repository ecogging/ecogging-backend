package com.pickupluck.ecogging.domain.forum.api;

import com.pickupluck.ecogging.domain.forum.dto.ForumDTO;
import com.pickupluck.ecogging.domain.forum.service.RouteService;
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
            res.put("shares",routes);
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
}
