package com.pickupluck.ecogging.domain.message.service;

import com.pickupluck.ecogging.domain.message.dto.request.MessageRoomRequestCreateDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomIdResponseDto;

public interface MessageRoomService {

    // 쪽지함 존재 확인 위한 검색
    public Long getMessageRoomId(Long userId, Long receiverId);

    //  쪽지함 생성
    public MessageRoomIdResponseDto saveMessageRoom(Long userId, MessageRoomRequestCreateDto request);

}
