package com.pickupluck.ecogging.domain.message.service;

import com.pickupluck.ecogging.domain.message.dto.request.MessageRequestSendDto;
import org.springframework.stereotype.Service;

public interface MessageService {

    // 쪽지 전송
    public void sendMessage(Long curId, Long messageRoomId, Long contactId, MessageRequestSendDto msgSendDto);


}
