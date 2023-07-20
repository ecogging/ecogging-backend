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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageRoomServiceI implements MessageRoomService{

    private final MessageRoomRepository messageRoomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    // 쪽지함 id 조회
    @Override
    @Transactional(readOnly = true)
    public Optional<Long> getMessageRoomId(Long curId, Long contactId) {
        User sender = null;
        User recevier = null;

        try {
            sender = userRepository.findById(curId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
            recevier = userRepository.findById(contactId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new); // 수신자 레포지 존재 여부 확인
        } catch (ChangeSetPersister.NotFoundException e) {
            System.out.println("주어진 id에 해당하는 사용자 정보가 없습니다");
        }

        return messageRoomRepository.findIdByInitialSenderAndInitialReceiver(sender, recevier);
    }


//    @Transactional // MessageRoom 생성 (MessageRoomId) -- userId, receiverId&firstMessage
//    public MessageRoomIdResponseDto saveMessageRoom(Long userId, MessageRoomRequestCreateDto request) {
//        if (userId == request.getReceiverId()) { // userId랑 만들고자하는 MessageRoom의 상대방Id(받은사람)가 같으면
//            return null;
//        }
//    }


}
