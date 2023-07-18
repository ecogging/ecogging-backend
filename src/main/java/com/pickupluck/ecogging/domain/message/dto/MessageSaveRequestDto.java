package com.pickupluck.ecogging.domain.message.dto;

import com.pickupluck.ecogging.domain.message.entity.Message;
import com.pickupluck.ecogging.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageSaveRequestDto {
    private String content;
    private Long receiverId;
    private Long senderId;

    @Builder
    public MessageSaveRequestDto(String content, Long receiverId, Long senderId) {
        this.content = content;
        this.receiverId = receiverId;
        this.senderId = senderId;
    }

    public static Message toEntity() {
        return Message.builder()
                .content(content)
                .receiver(receiver)
                .sender(sender)
                .build();
    }


}
