package com.pickupluck.ecogging.domain.posts;

import com.pickupluck.ecogging.domain.posts.PostResponseDto;
import com.pickupluck.ecogging.domain.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MainController {
    @Autowired
    private PostsService postsService;

    public MainController(PostsService postsService) {
        this.postsService=postsService;
    }

    @GetMapping("/api/list")
    public List<PostResponseDto> list() {
        List<PostResponseDto> postDtoList = postsService.findAll();
        System.out.println("오나료");
        return postDtoList;
//        return Arrays.asList("이거임?", "이거냐고..");
    }
}
