package com.pickupluck.ecogging.domain.comment.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentSaveRequest {

    private String content;

    private Long articleId;

    private Long parentId;
}
