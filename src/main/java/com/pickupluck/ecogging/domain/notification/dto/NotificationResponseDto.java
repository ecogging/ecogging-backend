package com.pickupluck.ecogging.domain.notification.dto;

import com.pickupluck.ecogging.domain.notification.entity.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDto {
    private Long id;

    private Long senderId;

    private String senderNickname;

    private Long targetId;

    private NotificationType type;

    private String detail;

    private LocalDateTime createdAt;
}
