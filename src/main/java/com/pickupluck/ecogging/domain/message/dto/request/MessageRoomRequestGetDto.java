package com.pickupluck.ecogging.domain.message.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRoomRequestGetDto {
    @NotNull
    private Long messageRoomId;
}
