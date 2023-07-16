package com.pickupluck.ecogging.domain.notification.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDto {

    private Long senderId;

    private String senderNickname;

    private Long targetId;

    private String typeName;

    private String detail;

    private LocalDateTime createdAt;
}
