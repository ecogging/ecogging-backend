package com.pickupluck.ecogging.domain.message.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 첫 화면 - 쪽지함 리스트
@Getter
public class MessageRoomListResponseDto {
    private final Long messageRoomId; // 쪽지함 id
    private final String contactNickname; // 쪽지 상대 닉네임
    private final LocalDateTime lastMessageSentTime; // 마지막 쪽지 보낸 시각
    private final String lastMessageContent; // 마지막 쪽지 내용
    private final Integer amIRead; // 읽음 여부

    @Builder
    public MessageRoomListResponseDto(Long msgRoomId, String contactNickname, LocalDateTime lastMessageSentTime, String lastMessageContent, Integer amIRead) {
        this.messageRoomId = msgRoomId;
        this.contactNickname = contactNickname;
        this.lastMessageSentTime = lastMessageSentTime;
        this.lastMessageContent = lastMessageContent;
        this.amIRead = amIRead;
    }
}
