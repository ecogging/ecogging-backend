package com.pickupluck.ecogging.domain.message.dto.response;

import com.pickupluck.ecogging.domain.message.entity.Message;
import com.pickupluck.ecogging.domain.message.entity.MessageContent;
import lombok.Getter;

@Getter
public class MessageContentResponseDto {
    private final Long contactId;
    private final String content;

    public MessageContentResponseDto(MessageContent msgContent, Message msg) {
        this.contactId = msg.getSender().getId();
        this.content = msgContent.getContent();
    }
}
