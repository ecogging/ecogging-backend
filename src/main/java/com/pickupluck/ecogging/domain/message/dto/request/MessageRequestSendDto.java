package com.pickupluck.ecogging.domain.message.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 쪽지 전송
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestSendDto {

    @NotBlank // Null "" " " 비허용
    private String message; // 전송할 쪽지 내용
    
}
