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

    private Long userId;

    private String userNickname;

    private Long articleId;

    private String content;

    private LocalDateTime createdAt;

    private Long parentId;

    private int depth;

    private String profileImageUrl;

    private boolean deleted;

    private List<CommentResponse> children;

    public static CommentResponse from(Comment comment) {
        Long parentId = comment.isParentExist() ? comment.getParent().getId() : null;
        List<CommentResponse> children = comment.isChildrenExist()
                ?
                    comment.getChildren()
                            .stream()
                            .map(c -> CommentResponse.from(c))
                            .sorted(Comparator.comparing(CommentResponse::getCreatedAt).reversed())
                            .toList()
                :
                    new ArrayList<>();

        return CommentResponse.builder()
                .id(comment.getId())
                .userId(comment.getWriter().getId())
                .userNickname(comment.getWriter().getNickname())
                .profileImageUrl(comment.getWriter().getProfileImageUrl())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .depth(comment.getDepth())
                .articleId(comment.getArticleId())
                .parentId(comment.isParentExist() ? comment.getParent().getId() : null)
                .children(children)
                .deleted(comment.getDeleted())
                .build();
    }
}
