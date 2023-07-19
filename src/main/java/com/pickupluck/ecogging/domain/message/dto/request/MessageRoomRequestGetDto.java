package com.pickupluck.ecogging.domain.message.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 쪽지함 불러오기
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRoomRequestGetDto {
    @NotNull
    private Long messageRoomId;
}
