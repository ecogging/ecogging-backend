package com.pickupluck.ecogging.domain.comment.service;

import com.pickupluck.ecogging.domain.comment.dto.CommentResponse;
import com.pickupluck.ecogging.domain.comment.dto.CommentSaveRequest;
import com.pickupluck.ecogging.domain.comment.entity.Comment;

import java.util.List;

public interface CommentService {

    List<CommentResponse> getMyComments() throws Exception;

    List<CommentResponse> findByAccompanyId(Long id) throws Exception;

    void save(CommentSaveRequest request) throws Exception;

    void delete(Long commentId) throws Exception;

}
