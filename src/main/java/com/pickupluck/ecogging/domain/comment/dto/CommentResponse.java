package com.pickupluck.ecogging.domain.comment.dto;


import com.pickupluck.ecogging.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    private int depth;

    private List<CommentResponse> children;

    public static CommentResponse from(Comment comment) {
        Long parentId = comment.isParentExist() ? comment.getParent().getId() : null;
        List<CommentResponse> children = comment.isChildrenExist()
                ?
                    comment.getChildren()
                            .stream()
                            .map(c -> CommentResponse.from(c))
                            .sorted(Comparator.comparing(CommentResponse::getCreatedAt))
                            .toList()
                :
                    new ArrayList<>();

        return CommentResponse.builder()
                .id(comment.getId())
                .writerId(comment.getWriter().getId())
                .writerName(comment.getWriter().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .depth(comment.getDepth())
                .parentId(comment.isParentExist() ? comment.getParent().getId() : null)
                .children(children)
                .build();
    }
}
