package com.pickupluck.ecogging.domain.message.dto.response;

import com.pickupluck.ecogging.domain.message.entity.MessageRoom;
import lombok.Getter;


// 상세 쪽지함
@Getter
public class MessageRoomIdResponseDto {
    private final Long msgRoomId;

    // Entity -> Dto
    public MessageRoomIdResponseDto(MessageRoom msgRoom) {
        this.msgRoomId = msgRoom.getId();
    }
}
