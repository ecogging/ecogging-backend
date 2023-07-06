package com.pickupluck.ecogging.domain.posts;

import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostResponseDto(Posts entity){
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.content=entity.getContent();
        this.author=entity.getAuthor();
    }

    @Override
    public String toString() {
        return "Posts [id=+"+id+",title="+title+",content="+content+",author="+author;
    }
}

// 조회 시 사용할 Dto
// entity를 dto화 시켜주는 method 추가

