package com.pickupluck.ecogging.domain.message.service;

import ch.qos.logback.core.spi.ErrorCodes;
import com.pickupluck.ecogging.domain.message.dto.request.MessageRoomRequestCreateDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomIdResponseDto;
import com.pickupluck.ecogging.domain.message.entity.Message;
import com.pickupluck.ecogging.domain.message.entity.MessageRoom;
import com.pickupluck.ecogging.domain.message.repository.MessageRepository;
import com.pickupluck.ecogging.domain.message.repository.MessageRoomRepository;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageRoomServiceI implements MessageRoomService{

    private final MessageRoomRepository messageRoomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Long getMessageRoomId(Long userId, Long receiverId) {
        userRepository.findById(userId);
        userRepository.findById(receiverId);

        return messageRoomRepository.findIdByInfo(userId, receiverId);
    }

    @Transactional // MessageRoom 생성 (MessageRoomId) -- userId, receiverId&firstMessage
    public MessageRoomIdResponseDto saveMessageRoom(Long userId, MessageRoomRequestCreateDto request) {
        if(userId == request.getReceiverId()) { // userId랑 만들고자하는 MessageRoom의 상대방Id(받은사람)가 같으면
            System.out.println("본인");
        }
        User currentUser = userRepository.findById(userId).get(); // 현재 유저 - 기준 (발신자)
        User receiver = userRepository.findById(request.getReceiverId()).get(); // 받는 사람 (수신자)

        MessageRoom messageRoom = MessageRoom.builder() // MessageRoom - 처음 수/발신자 설정해서 생성
                .initialSender(currentUser)
                .initialReceiver(receiver)
                .build();
        MessageRoom savedMessageRoom = messageRoomRepository.save(messageRoom); // 레포지토리에 messageRoom 저장

        Message message = Message.builder() // Message에 msgRoom, 발신자, 쪽지 내용 설정해서 생성
                .messageRoom(savedMessageRoom)
                .sender(currentUser)
                .content(request.getFirstMessage())
                .build();
        messageRepository.save(message); // 레포지토리에 message 저장
        
        return new MessageRoomIdResponseDto(savedMessageRoom); // MessageRoomId만 가진 Dto로 entity륿 변환해 리턴
    }


}
