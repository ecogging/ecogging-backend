package com.pickupluck.ecogging.domain.comment.service;

import com.pickupluck.ecogging.domain.BoardType;
import com.pickupluck.ecogging.domain.comment.dto.CommentResponse;
import com.pickupluck.ecogging.domain.comment.dto.CommentSaveRequest;

import java.util.List;

public interface CommentService {

    List<CommentResponse> getMyComments() throws Exception;

    List<CommentResponse> findByBoardTypeAndArticleId(BoardType boardType, Long articleId) throws Exception;

    void save(CommentSaveRequest request) throws Exception;

    void delete(Long commentId) throws Exception;

    void deleteByBoardTypeAndArticleId(BoardType boardType, Long articleId) throws Exception;
}
