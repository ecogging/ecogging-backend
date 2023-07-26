package com.pickupluck.ecogging.domain.message.service;

import com.pickupluck.ecogging.domain.message.dto.request.MessageRequestSendDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageResponseDto;
import com.pickupluck.ecogging.domain.message.entity.Message;
import com.pickupluck.ecogging.domain.message.entity.MessageRoom;
import com.pickupluck.ecogging.domain.message.entity.VisibilityState;
import com.pickupluck.ecogging.domain.message.repository.MessageRepository;
import com.pickupluck.ecogging.domain.message.repository.MessageRoomRepository;
import com.pickupluck.ecogging.domain.notification.dto.NotificationSaveDto;
import com.pickupluck.ecogging.domain.notification.entity.NotificationType;
import com.pickupluck.ecogging.domain.notification.service.NotificationService;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceI implements MessageService{

    private final MessageRoomRepository messageRoomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    private final NotificationService notificationService;


    // 1. 쪽지 전송
    @Override
    @Transactional
    public void sendMessage(Long curId, Long messageRoomId, Long contactId, MessageRequestSendDto msgSendDto) {

        // 현재 유저 조회 & 쪽지함 조회
        User currentUser = userRepository.findById(curId).get();
        User contactUser = userRepository.findById(contactId).get();
        MessageRoom messageRoom = messageRoomRepository.findById(messageRoomId).get();

        // 유저 쪽지 전송 권한 조회 & 삭제된 쪽지함인지 확인
        checkUserAuthority(currentUser, messageRoom);
        checkMessageRoomIsDeleted(messageRoom, currentUser.getId());

        // Message Entity 생성
        Message message = Message.builder()
                .messageRoom(messageRoom)
                .sender(currentUser)
                .receiver(contactUser)
                .content(msgSendDto.getMessage())
                .build();
        // Message Entity -> Repo 저장
        messageRepository.save(message);

        // notification
        // 발송: 쪽지 발송자
        final Long notiSenderId = message.getSender().getId();
        // 수신: 쪽지 수신자
        final Long notiReceiverId = message.getReceiver().getId();
        // 타겟은 메시지룸 아이디
        final Long notiTargetId = message.getMessageRoom().getId();
        // 디테일 없음
        final NotificationType notiType = NotificationType.MESSAGE;

        notificationService.createNotification(
                NotificationSaveDto.builder()
                        .receiverId(notiReceiverId)
                        .targetId(notiTargetId)
                        .senderId(notiSenderId)
                        .type(notiType)
                        .build()
        );
    }


    // 2. 쪽지 조회
    @Override
    @Transactional(readOnly = true)
    public Page<MessageResponseDto> getAllMessages(Long userId, Long messageRoomId, Pageable pageable) {
        // 현재 유저, 쪽지함 조회
        User currentUser = userRepository.findById(userId).get();
        MessageRoom messageRoom = messageRoomRepository.findById(messageRoomId).get();

        // 유저의 쪽지함 삭제 상태 확인
        checkMessageRoomIsDeleted(messageRoom, userId);

        // 페이징
        Page<Message> messages = messageRoomRepository.findMessagesByMessageRoomId(
                messageRoomId, pageable);

        // 관계 확인
        // - 현재유저 == 쪽지함 첫 발신자 => 첫 수신자가 상대방.
        // - 현재유저 != 쪽지함 첫 발신자 => 첫 발신자가 상대방.
        User contact =
                currentUser.getId() == messageRoom.getInitialSender().getId()
                        ? messageRoom.getInitialReceiver() : messageRoom.getInitialSender();

        return messages.map(message -> new MessageResponseDto(message, contact));
    }


    // 1-1. 쪽지 전송 권한 확인
    private void checkUserAuthority(User user, MessageRoom messageRoom) {
       // 쪽지함의 첫 수신자 or 첫 발신자가 현재 유저가 아니라면
        if (!(messageRoom.getInitialSender().getId() == user.getId())
            && !(messageRoom.getInitialReceiver().getId() == user.getId())) {
            throw new AccessDeniedException("쪽지 전송 권한이 없음");
        }
    }


    // 2-1. 쪽지 삭제 상태 확인
    private void checkMessageRoomIsDeleted(MessageRoom messageRoom, Long userId) {
        VisibilityState visibility = messageRoom.getVisibilityTo();
        if (visibility.equals(VisibilityState.NO_ONE) ||
                (visibility.equals(VisibilityState.ONLY_INITIAL_RECEIVER)) ||
                (visibility.equals(VisibilityState.ONLY_INITIAL_SENDER))) {
            
            // 쪽지함 복원
            messageRoom.setVisibilityTo(VisibilityState.BOTH);

        }
    }

}
