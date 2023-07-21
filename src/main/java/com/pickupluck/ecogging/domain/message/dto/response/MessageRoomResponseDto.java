package com.pickupluck.ecogging.domain.message.dto.response;

import com.pickupluck.ecogging.domain.message.entity.Message;
import com.pickupluck.ecogging.domain.message.entity.MessageRoom;
import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.validation.constraints.AssertFalse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class MessageRoomResponseDto {
    private final String contactNickname;
    private final Page<MessageResponseDto> messages;

    @Builder
    public MessageRoomResponseDto(MessageRoom messageRoom, Page<Message> messages, User contact) {
        this.contactNickname = contact.getNickname();
        this.messages = messages.map(message -> new MessageResponseDto(message, contact));
    }
}
