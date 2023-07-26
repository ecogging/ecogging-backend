package com.pickupluck.ecogging.domain.message.dto.response;

import com.pickupluck.ecogging.domain.message.entity.ReadState;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 첫 화면 - 쪽지함 리스트
@Getter
public class MessageRoomListResponseDto {
    private final Long messageRoomId; // 쪽지함 id
    private final String contactPicUrl; // 쪽지 상대 프로필사진 url
    private final String contactNickname; // 쪽지 상대 닉네임
    private final LocalDateTime lastMessageSentTime; // 마지막 쪽지 보낸 시각
    private final String lastMessageContent; // 마지막 쪽지 내용
    private final ReadState readBy; // 읽음여부
    private final Long initialSend;
    private final Long initialRcv;

    @Builder
    public MessageRoomListResponseDto(Long messageRoomId, String contactPicUrl, String contactNickname, LocalDateTime lastMessageSentTime, String lastMessageContent, ReadState readBy, Long initialSend, Long initialRcv) {
        this.messageRoomId = messageRoomId;
        this.contactPicUrl = contactPicUrl;
        this.contactNickname = contactNickname;
        this.lastMessageSentTime = lastMessageSentTime;
        this.lastMessageContent = lastMessageContent;
        this.readBy = readBy;
        this.initialSend = initialSend;
        this.initialRcv = initialRcv;
    }
}
