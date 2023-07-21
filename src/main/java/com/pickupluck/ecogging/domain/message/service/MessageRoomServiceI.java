package com.pickupluck.ecogging.domain.message.service;

import ch.qos.logback.core.spi.ErrorCodes;
import com.pickupluck.ecogging.domain.message.dto.MessageRoomsWithLastMessages;
import com.pickupluck.ecogging.domain.message.dto.request.MessageRoomRequestCreateDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomIdResponseDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomListResponseDto;
import com.pickupluck.ecogging.domain.message.entity.Message;
import com.pickupluck.ecogging.domain.message.entity.MessageRoom;
import com.pickupluck.ecogging.domain.message.repository.MessageRepository;
import com.pickupluck.ecogging.domain.message.repository.MessageRoomRepository;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
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
        Long senderId = null;
        Long recevierId = null;

        try {
            senderId = userRepository.findById(curId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new).getId();
            recevierId = userRepository.findById(contactId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new).getId(); // 수신자 레포지 존재 여부 확인
            System.out.println("발신자::"+senderId+", 수신자::"+recevierId); // 여기까지 진입 완료

            // 쪽지함 존재여부 조회
            Optional<Long> existedRoomIdOptional = messageRoomRepository.findIdByInitialSenderAndInitialReceiver(
                    senderId, recevierId);
            if(existedRoomIdOptional.isPresent() && existedRoomIdOptional.get() != null) {
                System.out.println("주어진 사용자 id들이 갖고있는 쪽지함 존재함");
                return existedRoomIdOptional;
            } else {
                Optional<Long> existedRoomIdOpitonalAgain = messageRoomRepository.findIdByInitialReceiverAndInitialSender(
                        senderId, recevierId);
                if(existedRoomIdOpitonalAgain.isPresent() && existedRoomIdOpitonalAgain.get() != null) {
                    System.out.println("주어진 사용자 id들이 갖고있는 쪽지함 존재함");
                    return existedRoomIdOpitonalAgain;
                } else {
                        System.out.println("주어진 사용자 id들이 갖고있는 쪽지함 존재하지 않음!");
                        return Optional.empty(); // 진입 완료 - 성공
                     }
            }
        } catch (ChangeSetPersister.NotFoundException e) {
            System.out.println("주어진 id에 해당하는 사용자 정보가 없습니다");
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public MessageRoomIdResponseDto saveMessageRoom(Long curId, Long contactId, String firstMessage) {

        if(curId == contactId) {
            throw new InvalidRequestStateException("**자기 자신에게 쪽지를 보낼 수 없음");
        }

        System.out.println("##saveMessageRoom() 함수 진입 완료");

        User sender = userRepository.findById(curId).get();
        User receiver = userRepository.findById(contactId).get();

        // MessageRoom Entity 생성
        MessageRoom messageRoom = MessageRoom.builder()
                .initialSender(sender)
                .initialReceiver(receiver)
                .build();

        // 생성한 MessageRoom Entity -> Repository에 저장
        MessageRoom savedMessageRoom = messageRoomRepository.save(messageRoom);

        System.out.println("MsgRoom Entity 생성 저장 완료");

        // Message Entity 생성
        Message message = Message.builder()
                .messageRoom(savedMessageRoom)
                .sender(sender)
                .receiver(receiver)
                .content(firstMessage)
                .deletedByRcv(0)
                .deletedBySnd(0)
                .read(0)
                .build();

        // 생성한 Message Entity -> Repository에 저장
        messageRepository.save(message);

        System.out.println("Msg Entity 생성 저장 완료");

        // 생성한 MessageRoomCreateDto return
        return new MessageRoomIdResponseDto(savedMessageRoom);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<MessageRoomListResponseDto> getMessageRooms(Long userId, Pageable pageable) {
        User currentUser = userRepository.findById(userId).get();

        // 페이지대로 정렬해서 리스트 조회
        Page<MessageRoomsWithLastMessages> messageRooms = messageRoomRepository.findMessageRoomsAndLastMessagesByUserId(
                currentUser.getId(), pageable);

        // map을 이용해 Page 내용 변환
        // userId와 msgRoom의 initialSenderId를 비교
        // -> 둘이 같으면 initialSenderId를 contactId로 설정
        // -> 같지 않으면 initialReceiverId를 contactId로 설정
        Page<MessageRoomListResponseDto> responses = messageRooms.map(messageRoom -> {
            Long contactId =
                    (userId == messageRoom.getInitialSenderId().longValue()) ?
                     messageRoom.getInitialReceiverId().longValue()
                    : messageRoom.getInitialSenderId().longValue();
            System.out.println("userID     "+userId);
            System.out.println("InitialSender  "+messageRoom.getInitialSenderId().longValue());
            System.out.println("InitialReceiver  "+messageRoom.getInitialReceiverId().longValue());
            System.out.println("********최종상대:   "+contactId);

            User contact = userRepository.findById(contactId).get();

            return MessageRoomListResponseDto.builder()
                    .msgRoomId(messageRoom.getMessageRoomId().longValue())
                    .contactNickname(contact.getNickname())
                    .lastMessageSentTime(messageRoom.getCreatedAt().toLocalDateTime())
                    .lastMessageContent(messageRoom.getContent())
                    .build();
        });

        return responses;
    }
}