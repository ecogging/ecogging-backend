package com.pickupluck.ecogging.domain.comment.service;

import com.pickupluck.ecogging.domain.BoardType;
import com.pickupluck.ecogging.domain.comment.dto.CommentResponse;
import com.pickupluck.ecogging.domain.comment.dto.CommentSaveRequest;
import com.pickupluck.ecogging.domain.comment.entity.Comment;
import com.pickupluck.ecogging.domain.comment.repository.CommentRepository;
import com.pickupluck.ecogging.domain.notification.dto.NotificationSaveDto;
import com.pickupluck.ecogging.domain.notification.entity.NotificationType;
import com.pickupluck.ecogging.domain.notification.service.NotificationService;
import com.pickupluck.ecogging.domain.plogging.dto.AccompanyDTO;
import com.pickupluck.ecogging.domain.plogging.entity.Accompany;
import com.pickupluck.ecogging.domain.plogging.service.AccompanyService;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import com.pickupluck.ecogging.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final NotificationService notificationService;

    private final AccompanyService accompanyService;

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
                .filter(comment -> !comment.isParentExist())
                .map(CommentResponse::from)
                .sorted(Comparator.comparing(CommentResponse::getCreatedAt).reversed())
                .toList();
    }

    @Override
    @Transactional
    public void save(CommentSaveRequest request) throws Exception {
        final String email = SecurityUtil.getCurrentUsername().orElse("");
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("No user for email: " + email));

        final Comment comment = Comment.builder()
                .content(request.getContent())
                .boardType(BoardType.ACCOMPANY) // 현재는 동행에만 댓글 달림
                .articleId(request.getArticleId())
                .writer(user)
                .build();

        if (request.getParentId() != null) {
            final Comment parent = commentRepository
                    .findById(request.getParentId())
                    .orElseThrow(()-> new IllegalArgumentException("no comment for id: " + request.getParentId()));

            if (parent.isParentExist()) {
                throw new Exception("대댓글의 대댓글을 달 수 없습니다.");
            }

            comment.registerParent(parent);
        }
        commentRepository.save(comment);

        // notification
        final boolean isReply = comment.isParentExist();
        final AccompanyDTO target = accompanyService.getAccompany(comment.getArticleId());

        // 대댓글일 경우, 부모댓글 작성자. 댓글일 경우 글 작성자
        final Long notiReceiverId = isReply ? comment.getParent().getWriter().getId() : target.getUserId();

        // 댓글 작성자
        final Long notiSenderId = user.getId();
        // 게시글 아이디
        final Long notiTargetId = target.getId();

        // 댓글일 경우, 글 제목. 대댓글일경우 없음
        final String notiDetail = isReply ? "" : target.getTitle();
        final NotificationType notiType = isReply ? NotificationType.REPLYCOMMENT : NotificationType.COMMENT;
        final BoardType notiBoardType = comment.getBoardType();

        notificationService.createNotification(
                NotificationSaveDto.builder()
                        .receiverId(notiReceiverId)
                        .senderId(notiSenderId)
                        .targetId(notiTargetId)
                        .detail(notiDetail)
                        .type(notiType)
                        .boardType(notiBoardType)
                        .build()
        );
    }

    @Override
    @Transactional
    public void delete(Long commentId) throws Exception {
        Comment findComment = commentRepository.findById(commentId)
                .orElseThrow(()->new Exception("no comment for id"));

        final String email = SecurityUtil.getCurrentUsername().orElse("");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("No user for email: " + email));

        // 다른 유저 댓글 삭제 불가
        if (!user.equals(findComment.getWriter()))
            throw new Exception(("남의 댓글은 지울 수 없습니다."));

        // 자식 댓글 있을 경우 safe delete
        if (findComment.isChildrenExist())
            findComment.delete();
        else
            commentRepository.delete(findComment);

    }
}
