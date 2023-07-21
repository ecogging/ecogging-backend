package com.pickupluck.ecogging.domain.message.service;

import com.pickupluck.ecogging.domain.message.dto.request.MessageRequestSendDto;
import com.pickupluck.ecogging.domain.message.entity.Message;
import com.pickupluck.ecogging.domain.message.entity.MessageRoom;
import com.pickupluck.ecogging.domain.message.repository.MessageRepository;
import com.pickupluck.ecogging.domain.message.repository.MessageRoomRepository;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

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

    // 3. 쪽지 전송 권한 확인
    private void checkUserAuthority(User user, MessageRoom messageRoom) {
       // 쪽지함의 첫 수신자 or 첫 발신자가 현재 유저가 아니라면
        if (!(messageRoom.getInitialSender().getId() == user.getId())
            && !(messageRoom.getInitialReceiver().getId() == user.getId())) {
            throw new AccessDeniedException("쪽지 전송 권한이 없음");
        }
    }


}
