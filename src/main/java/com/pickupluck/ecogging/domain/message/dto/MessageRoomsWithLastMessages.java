package com.pickupluck.ecogging.domain.message.dto;

import com.pickupluck.ecogging.domain.message.entity.ReadState;

import java.sql.Timestamp;

public interface MessageRoomsWithLastMessages {
    Long getMessageRoomId(); // 쪽지함 id 조회
    Long getInitialReceiverId(); // 처음 수신자 id 조회
    Long getInitialSenderId(); // 처음 발신자 id 조회
    Timestamp getCreatedAt(); // 생성일시 조회
    String getContent(); // 쪽지 내용 조회
    ReadState getReadBy();
}
