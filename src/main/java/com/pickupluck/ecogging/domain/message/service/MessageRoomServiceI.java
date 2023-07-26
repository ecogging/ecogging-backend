package com.pickupluck.ecogging.domain.message.service;

import ch.qos.logback.core.spi.ErrorCodes;
import com.pickupluck.ecogging.domain.message.dto.MessageRoomsWithLastMessages;
import com.pickupluck.ecogging.domain.message.dto.request.MessageRoomRequestCreateDto;
import com.pickupluck.ecogging.domain.message.dto.request.MessageRoomRequestGetDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomIdResponseDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomListResponseDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomResponseDto;
import com.pickupluck.ecogging.domain.message.entity.Message;
import com.pickupluck.ecogging.domain.message.entity.MessageRoom;
import com.pickupluck.ecogging.domain.message.entity.ReadState;
import com.pickupluck.ecogging.domain.message.entity.VisibilityState;
import com.pickupluck.ecogging.domain.message.repository.MessageRepository;
import com.pickupluck.ecogging.domain.message.repository.MessageRoomRepository;
import com.pickupluck.ecogging.domain.notification.dto.NotificationSaveDto;
import com.pickupluck.ecogging.domain.notification.entity.NotificationType;
import com.pickupluck.ecogging.domain.notification.service.NotificationService;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageRoomServiceI implements MessageRoomService {

    private final MessageRoomRepository messageRoomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    private final NotificationService notificationService;

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
            System.out.println("발신자::" + senderId + ", 수신자::" + recevierId); // 여기까지 진입 완료

            // 쪽지함 존재여부 조회
            Optional<Long> existedRoomIdOptional = messageRoomRepository.findIdByInitialSenderAndInitialReceiver(
                    senderId, recevierId);
            if (existedRoomIdOptional.isPresent() && existedRoomIdOptional.get() != null) {
                System.out.println("주어진 사용자 id들이 갖고있는 쪽지함 존재함");
                return existedRoomIdOptional;
            } else {
                Optional<Long> existedRoomIdOpitonalAgain = messageRoomRepository.findIdByInitialReceiverAndInitialSender(
                        senderId, recevierId);
                if (existedRoomIdOpitonalAgain.isPresent() && existedRoomIdOpitonalAgain.get() != null) {
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

    // 쪽지함 생성
    @Override
    @Transactional
    public MessageRoomIdResponseDto saveMessageRoom(Long curId, Long contactId, String firstMessage) {

        if (curId == contactId) {
            throw new InvalidRequestStateException("**자기 자신에게 쪽지를 보낼 수 없음");
        }

        User sender = userRepository.findById(curId).get();
        User receiver = userRepository.findById(contactId).get();

        // MessageRoom Entity 생성
        MessageRoom messageRoom = MessageRoom.builder()
                .initialSender(sender)
                .initialReceiver(receiver)
                .build();

        // 생성한 MessageRoom Entity -> Repository에 저장
        MessageRoom savedMessageRoom = messageRoomRepository.save(messageRoom);

        // Message Entity 생성
        Message message = Message.builder()
                .messageRoom(savedMessageRoom)
                .sender(sender)
                .receiver(receiver)
                .content(firstMessage)
                .build();

        // 생성한 Message Entity -> Repository에 저장
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

        // 생성한 MessageRoomCreateDto return
        return new MessageRoomIdResponseDto(savedMessageRoom);

    }

    // 쪽지함 조회해서 리스트로 반환
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object>  getMessageRooms(Long userId, Pageable pageable) {
        User currentUser = userRepository.findById(userId).get();

        // 페이지대로 정렬해서 리스트 조회
        Page<MessageRoomsWithLastMessages> messageRooms = messageRoomRepository.findMessageRoomsAndLastMessagesByUserId(
                currentUser.getId(), pageable);

        // 쿼리에 맞는 모든 데이터 -> 전체 개수
        List<MessageRoomsWithLastMessages> alls = messageRoomRepository.findMessageRoomsAndLastMessagesByUserId(currentUser.getId());
        int count = alls.size();


        // map을 이용해 Page 내용 변환
        // userId와 msgRoom의 initialSenderId를 비교
        // -> 둘이 같으면 initialSenderId를 contactId로 설정
        // -> 같지 않으면 initialReceiverId를 contactId로 설정
        Page<MessageRoomListResponseDto> responses = messageRooms.map(messageRoom -> {
            Long contactId =
                    (userId == messageRoom.getInitialSenderId().longValue()) ?
                            messageRoom.getInitialReceiverId().longValue()
                            : messageRoom.getInitialSenderId().longValue();

            User contact = userRepository.findById(contactId).get();

            return MessageRoomListResponseDto.builder()
                    .messageRoomId(messageRoom.getMessageRoomId().longValue())
                    .contactPicUrl(contact.getProfileImageUrl())
                    .contactNickname(contact.getNickname())
                    .lastMessageSentTime(messageRoom.getCreatedAt().toLocalDateTime())
                    .lastMessageContent(messageRoom.getContent())
                    .readBy(messageRoom.getReadBy())
                    .initialSend(messageRoom.getInitialSenderId())
                    .initialRcv(messageRoom.getInitialReceiverId())
                    .build();
        });

        // 결과 담아서 넘기는 맵
        Map<String, Object> result = new HashMap<>();
        result.put("res", responses); // 해당 페이지에 띄울 글 목록
        result.put("all", count); // 페이징을 위한 전체 데이터 개수

        return result;
    }

    // 쪽지함 조회해서 상세 쪽지목록 있는 쪽지함 반환
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getMessageRoom(Long userId, MessageRoomRequestGetDto requestGetDto, int pageNo) {
        // 1. 현재 유저 조회
        User currentUser = userRepository.findById(userId).get();
        // 2. 쪽지함레포지에서 쪽지함id로 검색해 조회
        MessageRoom messageRoom = messageRoomRepository.findById(requestGetDto.getMessageRoomId()).get();

        // 삭제 상태 확인
        checkMessageRoomIsDeleted(messageRoom, userId);

        // 페이징
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by("createdAt").descending());

        Page<Message> messages = null;
        List<Message> alls = null;
        Integer count = null;

        // 현재 유저가 initialSENDER
        if(messageRoom.getInitialSender().getId() == userId) {
            messages = messageRoomRepository.findMessagesByMessageRoomIdAndSender(
                    messageRoom.getId(), pageable);
            alls = messageRoomRepository.findMessagesByMessageRoomIdAndSender(currentUser.getId());
            count=alls.size();
        } else {
            messages = messageRoomRepository.findMessagesByMessageRoomIdAndReceiver(
                    messageRoom.getId(), pageable);
            // 쿼리에 맞는 모든 데이터 -> 전체 개수
            alls = messageRoomRepository.findMessagesByMessageRoomIdAndReceiver(currentUser.getId());
            count=alls.size();
        }

        User contact =
                (currentUser.getId() == messageRoom.getInitialSender().getId()) ?
                messageRoom.getInitialReceiver() : messageRoom.getInitialSender();

        MessageRoomResponseDto responseDto = MessageRoomResponseDto.builder()
                                            .messages(messages)
                                            .messageRoom(messageRoom)
                                            .contact(contact)
                                            .build();

        Map<String, Object> result = new HashMap<>();
        result.put("res", responseDto); // 해당 페이지에 띄울 글 목록
        result.put("all", count); // 페이징을 위한 전체 데이터 개수

        return result;

    }

    // 쪽지함 삭제 - Enum 상태만 변경
    @Override
    @Transactional
    public void deleteMessageRoom(Long userId, Long messageRoomId) {
        // 현재 유저 조회
        User currentUser = userRepository.findById(userId).get();
        // 쪽지함 조회
        MessageRoom messageRoom = messageRoomRepository.findById(messageRoomId).get();
        // 쪽지함에 포함된 쪽지목록 조회
        List<Message> messagesInTheRoom = messageRepository.findAllByMessageRoomId(messageRoomId);

        // 삭제 권한 확인
        checkUserAuthority(currentUser, messageRoom);

        // 삭제상태 수정 -- MessageRoom
        VisibilityState visibilityState = isInitialSender(currentUser, messageRoom) ?
                VisibilityState.ONLY_INITIAL_RECEIVER : VisibilityState.ONLY_INITIAL_SENDER;
        messageRoom.changeVisibilityTo(visibilityState);

        // 삭제상태 수정 -- Message ( 쪽지함과 연동 )
        for (Message m : messagesInTheRoom) {
            m.changeVisibilityTo(visibilityState);
        }
    }

    // 쪽지함 삭제한 사람인지 확인
    private void checkMessageRoomIsDeleted(MessageRoom messageRoom, Long userId) {
        VisibilityState visibility = messageRoom.getVisibilityTo(); // MessageRoom Enum (삭제 상태) 확인
        if (visibility.equals(VisibilityState.NO_ONE) ||
                (messageRoom.getInitialSender().getId() == userId &&
                        visibility.equals(VisibilityState.ONLY_INITIAL_RECEIVER)) ||
                (messageRoom.getInitialReceiver().getId() == userId &&
                        visibility.equals(VisibilityState.ONLY_INITIAL_SENDER))) {
            throw new PermissionDeniedDataAccessException("접근불가능", new Throwable());
        }
    }

    // 쪽지함 수정(삭제/차단) 권한 확인
    private void checkUserAuthority(User user, MessageRoom messageRoom) {
        if (!(messageRoom.getInitialSender().getId() == user.getId()) && // 첫 발신자 == 현재 유저가 아니고
                !(messageRoom.getInitialReceiver().getId() == user.getId())) { // 첫 수신자 == 현재 유저가 아니라면 권한 X
            throw new PermissionDeniedDataAccessException("권한 없는 사용자", new Throwable());
        }
    }

    
    //현재 유저가 첫 발신자인지 확인
    private boolean isInitialSender(User user, MessageRoom messageRoom) {
        if (messageRoom.getInitialSender().getId() == user.getId()) {
            return true;
        }
        return false;
    }


    // 읽음 여부 처리
    @Override
    @Transactional
    public void updateMessagesRead(Long userId, Long messageRoomId) {

        // 현재 유저
        User now = userRepository.findById(userId).get();
        // 선택된 쪽지함
        MessageRoom msgRoom = messageRoomRepository.findById(messageRoomId).get();
        // 쪽지함에 포함된 쪽지목록 조회
        List<Message> messagesInTheRoom = messageRepository.findAllByMessageRoomId(messageRoomId);

        // 읽음 상태 업데이트
        ReadState readState = isInitialSender(now, msgRoom) ?
                ReadState.ONLY_INITIAL_SENDER : ReadState.ONLY_INITIAL_RECEIVER;

        for (Message m : messagesInTheRoom) {
            m.changeReadBy(readState);
        }

        // 쪽지함도 같은 값으로 변경
        msgRoom.changeReadBy(readState);
    }

    // 읽음 여부 처리 (모두)
    @Override
    @Transactional
    public void updateMessagesReadAll(Long userId, Long messageRoomId) {

        // 현재 유저
        User now = userRepository.findById(userId).get();
        // 선택된 쪽지함
        MessageRoom msgRoom = messageRoomRepository.findById(messageRoomId).get();
        // 쪽지함에 포함된 쪽지목록 조회
        List<Message> messagesInTheRoom = messageRepository.findAllByMessageRoomId(messageRoomId);

        // 읽음 상태 업데이트
        for (Message m : messagesInTheRoom) {
            m.changeReadBy(ReadState.BOTH);
            System.out.println(m.getReadBy());
        }

        // 쪽지함도 같은 값으로 변경
        msgRoom.changeForce(ReadState.BOTH);
        System.out.println(msgRoom.getReadBy());
    }
}