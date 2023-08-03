package com.pickupluck.ecogging.domain.comment.repository;

import com.pickupluck.ecogging.domain.BoardType;
import com.pickupluck.ecogging.domain.comment.entity.Comment;
import com.pickupluck.ecogging.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBoardTypeAndArticleId(BoardType boardType, Long articleId);

    List<Comment> findByWriter(User Writer);

    void deleteByBoardTypeAndArticleId(BoardType boardType, Long articleId);
}
