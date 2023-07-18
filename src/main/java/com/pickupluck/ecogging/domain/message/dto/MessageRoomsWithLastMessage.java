package com.pickupluck.ecogging.domain.message.dto;

import java.math.BigInteger;
import java.sql.Timestamp;

public interface MessageRoomsWithLastMessage {
    BigInteger getMessageRoomId();
    BigInteger getInitialReceiverId();
    BigInteger getInitialSenderId();
    Timestamp getCreatedAt();
    String getContent();
}
