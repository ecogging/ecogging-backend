package com.pickupluck.ecogging.domain.notification.dto;

import com.pickupluck.ecogging.domain.notification.entity.NotificationType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class NotificationSaveDto {

    private Long senderId;

    private Long receiverId;

    private Long targetId;

    private NotificationType type;

    private String detail;
}
