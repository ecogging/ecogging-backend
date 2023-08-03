package com.pickupluck.ecogging.domain.scrap.api;

import com.pickupluck.ecogging.domain.forum.entity.Forum;
import com.pickupluck.ecogging.domain.scrap.service.ForumScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ForumScrapController {

    @Autowired
    private ForumScrapService forumScrapService;

    //forum scrap
    @PostMapping("/forumscrap/{forumId}/{userId}")
    public ResponseEntity<Boolean> forumScrap(@PathVariable Long forumId, @PathVariable Long userId){

        try {
            System.out.println("나눔 스크랩 컨트롤러 들어오기 성고옹");
            Boolean isScrap = forumScrapService.setForumScrap(forumId,userId);
            return new ResponseEntity<>(isScrap,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
