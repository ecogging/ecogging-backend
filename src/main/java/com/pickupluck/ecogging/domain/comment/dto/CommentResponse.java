package com.pickupluck.ecogging.domain.comment.dto;


import com.pickupluck.ecogging.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentResponse {

    private Long id;

    private Long writerId;

    private String writerName;

    private String content;

    private LocalDateTime createdAt;

    private Long parentId;

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .writerId(comment.getWriter().getId())
                .writerName(comment.getWriter().getName())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .parentId(comment.getParent().getId())
                .build();
    }
}
