package com.pickupluck.ecogging.domain.message.service;

import com.pickupluck.ecogging.domain.message.dto.request.MessageRequestSendDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageResponseDto;
import com.pickupluck.ecogging.domain.message.entity.Message;
import com.pickupluck.ecogging.domain.message.entity.MessageRoom;
import com.pickupluck.ecogging.domain.message.entity.VisibilityState;
import com.pickupluck.ecogging.domain.message.repository.MessageRepository;
import com.pickupluck.ecogging.domain.message.repository.MessageRoomRepository;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageServiceI implements MessageService{

    private final MessageRoomRepository messageRoomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    // 1. 쪽지 전송
    @Override
    @Transactional
    public void sendMessage(Long curId, Long messageRoomId, Long contactId, MessageRequestSendDto msgSendDto) {

        // 현재 유저 조회 & 쪽지함 조회
        User currentUser = userRepository.findById(curId).get();
        User contactUser = userRepository.findById(contactId).get();
        MessageRoom messageRoom = messageRoomRepository.findById(messageRoomId).get();

        // 유저 쪽지 권한 조회
        checkUserAuthority(currentUser, messageRoom);

        // Message Entity 생성
        Message message = Message.builder()
                .messageRoom(messageRoom)
                .sender(currentUser)
                .receiver(contactUser)
                .content(msgSendDto.getMessage())
                .read(0)
                .build();
        // Message Entity -> Repo 저장
        messageRepository.save(message);
        System.out.println("쪽지 전송 완료!!!! ^_______________^ //// ");
    }

    // 2. 쪽지 조회
//    @Override
//    @Transactional(readOnly = true)
//    public Page<MessageResponseDto> getAllMessages(Long curId, Long messageRoomId, Pageable pageable) {
//        // 현재 유저, 쪽지함 조회
//        User currentUser = userRepository.findById(curId).get();
//        MessageRoom messageRoom = messageRoomRepository.findById(messageRoomId).get();
//
//        // 유저의 쪽지함 삭제 상태 확인
//        checkMessageRoomIsDeleted(messageRoom, curId);
//
//        // 페이징
//        Page<Message> messages = messageRoomRepository.findMessagesByMessageRoomId(
//                messageRoomId, pageable);
//
//        // 관계 확인
//        // - 현재유저 == 쪽지함 첫 발신자 => 첫 수신자가 상대방.
//        // - 현재유저 != 쪽지함 첫 발신자 => 첫 발신자가 상대방.
//        User contact =
//                currentUser.getId() == messageRoom.getInitialSender().getId()
//                        ? messageRoom.getInitialReceiver() : messageRoom.getInitialSender();
//
//        return messages.map(message -> new MessageResponseDto(message, contact));
//    }

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
                (messageRoom.getInitialSender().getId() == userId &&
                        visibility.equals(VisibilityState.ONLY_INITIAL_RECEIVER)) ||
                (messageRoom.getInitialReceiver().getId() == userId &&
                        visibility.equals(VisibilityState.ONLY_INITIAL_SENDER))) {
            throw new PermissionDeniedDataAccessException("이미 삭제한 쪽지에 접근 불가능", new Throwable());
        }
    }

}
