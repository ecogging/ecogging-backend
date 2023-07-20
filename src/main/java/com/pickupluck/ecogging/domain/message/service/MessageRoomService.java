package com.pickupluck.ecogging.domain.message.service;

import com.pickupluck.ecogging.domain.message.dto.request.MessageRoomRequestCreateDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomIdResponseDto;

import java.util.Optional;

public interface MessageRoomService {

    // 쪽지함 id 조회
    public Optional<Long> getMessageRoomId(Long curId, Long contactId);

    // 쪽지함 생성하고 생성ResponseDto 반환
    public MessageRoomIdResponseDto saveMessageRoom(Long curId, Long contactId, String firstMessage);

}
