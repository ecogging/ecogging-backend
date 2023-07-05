package com.pickupluck.ecogging.domain.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional // 메서드 실행시 트랜잭션 시작 -> 정상종료되면 커밋, 에러나면 롤백
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
 
// 게시글 내용을 담은 dto를 받아와 entity화시켜 save
// save가 잘 됐는지 보기 위해 게시글 id반환