package com.pickupluck.ecogging.domain.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor // Bean 주입 -> @Autowired 대신 생성자로
@RestController
public class PostsApiController {
    private final PostsService postsServcie;

    @PostMapping("/saveForm/post") // 요청이 들어오면
    public Long save(@RequestBody PostsSaveRequestDto requestDto){ // dto에 내용이 담겨 넘어와서
        return postsServcie.save(requestDto); // postService의 save method로 넘겨줌
    }

}
