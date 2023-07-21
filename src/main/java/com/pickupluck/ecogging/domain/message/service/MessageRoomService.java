package com.pickupluck.ecogging.domain.message.service;

import com.pickupluck.ecogging.domain.message.dto.request.MessageRoomRequestCreateDto;
import com.pickupluck.ecogging.domain.message.dto.request.MessageRoomRequestGetDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomIdResponseDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomListResponseDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomResponseDto;
import com.pickupluck.ecogging.domain.message.entity.MessageRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface MessageRoomService {

    // 쪽지함 id 조회
    Optional<Long> getMessageRoomId(Long curId, Long contactId);

    // 쪽지함 생성하고 생성ResponseDto 반환
    MessageRoomIdResponseDto saveMessageRoom(Long curId, Long contactId, String firstMessage);

    // 쪽지함 리스트 조회한 페이지 반환
    Page<MessageRoomListResponseDto> getMessageRooms(Long userId, Pageable pageable);

    // 쪽지함 상세 조회
    MessageRoomResponseDto getMessageRoom(Long userId, MessageRoomRequestGetDto requestGetDto);

}

