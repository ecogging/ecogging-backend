package com.pickupluck.ecogging.domain.message.service;

import com.pickupluck.ecogging.domain.message.dto.request.MessageRoomRequestCreateDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomIdResponseDto;

import java.util.Optional;

public interface MessageRoomService {

    // 쪽지함 id 조회
    public Optional<Long> getMessageRoomId(Long curId, Long contactId);

}
