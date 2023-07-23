package com.pickupluck.ecogging.domain.message.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


// 쪽지함 생성 DTO
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRoomRequestCreateDto {

    @NotNull // Null만 비허용 "" or " "허용
    private Long senderId; // 발신자 id
    @NotNull
    private Long receiverId; // 수신자 id
    @NotNull
    private String firstMessage; // 쪽지함 첫 시작 쪽지
    
}
