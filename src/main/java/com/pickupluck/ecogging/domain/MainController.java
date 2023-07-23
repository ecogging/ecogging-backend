package com.pickupluck.ecogging.domain;

import com.pickupluck.ecogging.domain.plogging.dto.AccompanyDTO;
import com.pickupluck.ecogging.domain.plogging.service.AccompanyService;
import com.pickupluck.ecogging.domain.posts.PostResponseDto;
import com.pickupluck.ecogging.domain.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MainController {

    @Autowired
    private AccompanyService accompanyService;

    @GetMapping("/main/accompanies")
    public Map<String,Object> accompList() {
        try {
            Map < String, Object > map = accompanyService.getMainAccompanyList();
            return map;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
