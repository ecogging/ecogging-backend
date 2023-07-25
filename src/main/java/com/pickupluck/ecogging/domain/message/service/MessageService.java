package com.pickupluck.ecogging.domain.message.service;

import com.pickupluck.ecogging.domain.message.dto.request.MessageRequestSendDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface MessageService {

    // 쪽지 전송
    void sendMessage(Long curId, Long messageRoomId, Long contactId, MessageRequestSendDto msgSendDto);

    // 모든 쪽지 조회
    Page<MessageResponseDto> getAllMessages(Long curId, Long messageRoomId, Pageable pageable);
}
