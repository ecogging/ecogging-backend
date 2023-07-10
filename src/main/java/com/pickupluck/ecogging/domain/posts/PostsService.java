package com.pickupluck.ecogging.domain.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional // 메서드 실행시 트랜잭션 시작 -> 정상종료되면 커밋, 에러나면 롤백
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    public List<PostResponseDto> findAll() {
        List<Posts> postsList = postsRepository.findAll();
        List<PostResponseDto> postsDtoList = new ArrayList<>();
        for(int i=0; i<postsList.size(); i++){
            PostResponseDto postDto = new PostResponseDto(postsList.get(i));
            postsDtoList.add(postDto);
        }
        return postsDtoList;
    }

}
 
// 게시글 내용을 담은 dto를 받아와 entity화시켜 save
// save가 잘 됐는지 보기 위해 게시글 id반환