package com.pickupluck.ecogging.domain.message.dto.response;

import com.pickupluck.ecogging.domain.message.entity.Message;
import com.pickupluck.ecogging.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;


// 쪽지함 상세
@Getter
public class MessageResponseDto {
    private final Integer isReceived; // 발신 or 수신
    private final LocalDateTime createdAt; // 쪽지 작성 일시
    private final String content;

    public MessageResponseDto(Message msg, User contact) {
        this.isReceived = contact.getId() == msg.getSender().getId() ? 1 : 0; // 상대id == msg발신자id 같으면 수신 (1)
        this.createdAt = msg.getCreatedAt();
        this.content = msg.getContent();
    }
}
