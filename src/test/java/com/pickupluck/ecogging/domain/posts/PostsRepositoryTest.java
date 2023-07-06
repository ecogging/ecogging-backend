package com.pickupluck.ecogging.domain.posts;

import org.assertj.core.api.Assert;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PostsRepositoryTest {
    @Autowired
    private PostsRepository postsRepository;

//    @AfterEach // 단위 테스트 끝날 때마다 실행되는 method -- 테스트 끝나면 내용 삭제
//    public void cleanup(){
//        postsRepository.deleteAll();
//    }

    @Test
    public void 게시글등록_불러오기테스트(){
        //given
        String title="TEMP TITLE 2";
        String content="TEMP CONTENT 2";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("TEMP AUTHOR 1")
                .build()); // 게시글 임의 등록

        //when
        List<Posts> postsList = postsRepository.findAll(); // 게시글 등록됐는지 확인 위해 불러오기

        //then -- given과 when 비교
        Posts posts = postsList.get(0);
        AssertionsForClassTypes.assertThat(posts.getTitle().equals(title));
        AssertionsForClassTypes.assertThat(posts.getContent().equals(content));

        System.out.println("불러온 포스트: "+posts);
    }

    @Test
    public void 저장된포스트(){
        List<Posts> postsList = postsRepository.findAll();
        for(int i=0; i<postsList.size(); i++){
            Posts post = postsList.get(i);
            System.out.println("포스트"+i+"----------");
            System.out.println(post.getTitle());
            System.out.println(post.getContent());
        }
    }


    

}
