package com.pickupluck.ecogging.domain.comment.service;

import com.pickupluck.ecogging.domain.BoardType;
import com.pickupluck.ecogging.domain.comment.dto.CommentResponse;
import com.pickupluck.ecogging.domain.comment.dto.CommentSaveRequest;
import com.pickupluck.ecogging.domain.comment.entity.Comment;
import com.pickupluck.ecogging.domain.comment.repository.CommentRepository;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import com.pickupluck.ecogging.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    @Override
    public List<CommentResponse> getMyComments() throws Exception {
        final String email = SecurityUtil.getCurrentUsername().orElse("");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("No user for email: " + email));

        return commentRepository.findByWriter(user)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    @Override
    public List<CommentResponse> findByAccompanyId(Long id) throws Exception {
        final BoardType boardType = BoardType.ACCOMPANY;

        return commentRepository.findByBoardTypeAndArticleId(boardType, id)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }

    @Override
    public void save(CommentSaveRequest request) throws Exception {
        final String email = SecurityUtil.getCurrentUsername().orElse("");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("No user for email: " + email));

        Comment parent = commentRepository.findById(request.getParentId()).orElse(null);
        Comment comment = Comment.builder()
                .content(request.getContent())
                .boardType(BoardType.ACCOMPANY) // 현재는 동행에만 댓글 달림
                .articleId(request.getArticleId())
                .writer(user)
                .build();

        comment.registerParent(comment);

        commentRepository.save(comment);
    }

    @Override
    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
