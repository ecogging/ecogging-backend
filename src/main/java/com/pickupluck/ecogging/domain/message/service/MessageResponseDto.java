package com.pickupluck.ecogging.domain.message.service;

import com.pickupluck.ecogging.domain.message.entity.Message;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MessageResponseDto {
    private Long id;
    private String content;
    private Integer read;
    private LocalDateTime createdAt;

    private Long receiverId;
    private Long senderId;
    private String senderNickname;


    public MessageResponseDto(Message entity) {
        this.id=entity.getId();
        this.content=entity.getContent();
        this.read=entity.getRead();
        this.createdAt=entity.getCreatedAt();
        this.receiverId=entity.getReceiver().getId();
        this.senderId=entity.getSender().getId();
        this.senderNickname=entity.getReceiver().getNickname();
    }

    @Override
    public String toString() {
        return "MessageResponseDto{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", read=" + read +
                ", createdAt=" + createdAt +
                ", receiverId=" + receiverId +
                ", senderId=" + senderId +
                ", senderNickname='" + senderNickname + '\'' +
                '}';
    }
}
