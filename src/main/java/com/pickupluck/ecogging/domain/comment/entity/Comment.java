package com.pickupluck.ecogging.domain.comment.entity;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;

import static org.springframework.util.StringUtils.hasText;

import com.pickupluck.ecogging.domain.BoardType;
import com.pickupluck.ecogging.domain.BaseEntity;
import com.pickupluck.ecogging.domain.user.entity.User;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @NotBlank
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private Long articleId;

    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @Builder
    public Comment(String content, BoardType boardType, Long articleId, User writer) {
        this.content = content;
        this.boardType = boardType;
        this.articleId = articleId;
        this.writer = writer;
    }

    public void addChildren(Comment comment) {
        children.add(comment);
    }

    public void registerParent(Comment comment) {
        this.parent = comment;
        comment.addChildren(this);
    }

    public void updateContent(String content) {
        if (!hasText(content))
            return;
        this.content = content;
    }

}
