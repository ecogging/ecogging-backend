package com.pickupluck.ecogging.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// dto =/= domain
// domain = DB와 밀접하고 중요한 entity 클래스가 들어있음
// -- entity는 한 번 정의하면 그 내용이 유지되어야 함 - 사용자에 의해 내용 변경 X
// dto = 화면에서 직접 받고 넘기는 객체
@NoArgsConstructor // 기본생성자
@Getter
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // Dto에서 필요한 부분을 entity화 시킴
    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}

// entity 클래스와 비슷하지만, entity클래스로 데이터를 주고받을 수는 없음
// 이 dto가 저장될 때 entity로 변환되어 저장되어야 하기 때문에 toEntity method 만들어줌