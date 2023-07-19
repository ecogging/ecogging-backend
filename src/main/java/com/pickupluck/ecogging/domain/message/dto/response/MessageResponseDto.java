package com.pickupluck.ecogging.domain.message.dto.response;

import com.pickupluck.ecogging.domain.message.entity.Message;
import com.pickupluck.ecogging.domain.message.entity.MessageContent;
import com.pickupluck.ecogging.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;


// 쪽지함 상세
@Getter
public class MessageResponseDto {
    private final String contactNickname; // 상대 닉네임
    private final Long contactId; // 상대 id
    private final LocalDateTime createdAt; // 쪽지 작성 일시
    private final Integer isReceived; // 발신 or 수신


    public MessageResponseDto(Message msg, User contact) {
        this.isReceived = Objects.equals(contact.getId(), msg.getSender().getId()) ? 1 : 0; // 상대id == msg발신자id 같으면 수신 (1)
        this.contactNickname = msg.getSender().getNickname(); // msg 발신자 닉네임
        this.contactId = msg.getSender().getId();
        this.createdAt = msg.getCreatedAt();
    }
}
