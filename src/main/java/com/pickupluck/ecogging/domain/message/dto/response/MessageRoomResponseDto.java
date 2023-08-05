package com.pickupluck.ecogging.domain.message.dto.response;

import com.pickupluck.ecogging.domain.message.entity.Message;
import com.pickupluck.ecogging.domain.message.entity.MessageRoom;
import com.pickupluck.ecogging.domain.message.entity.ReadState;
import com.pickupluck.ecogging.domain.user.entity.User;
import jakarta.validation.constraints.AssertFalse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MessageRoomResponseDto {
    private final Long contactId;
    private final String contactNickname;
    private final String contactPicUrl;
    private final Slice<MessageResponseDto> messages;

    @Builder
    public MessageRoomResponseDto(MessageRoom messageRoom, Slice<Message> messages, User contact) {
        this.contactId = contact.getId();
        this.contactNickname = contact.getNickname();
        this.contactPicUrl = contact.getProfileImageUrl();
        this.messages = messages.map(message -> new MessageResponseDto(message, contact));
    }
}
