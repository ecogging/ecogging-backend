package com.pickupluck.ecogging.domain.notification.dto;

import com.pickupluck.ecogging.domain.BoardType;
import com.pickupluck.ecogging.domain.notification.entity.Notification;
import com.pickupluck.ecogging.domain.notification.entity.NotificationType;
import com.pickupluck.ecogging.domain.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDto {
    private Long id;

    private Long receiverId;

    private Long targetId;

    private Long senderId;

    private String senderNickname;

    private NotificationType type;

    private BoardType boardType; // 타겟 아이디가 특정 게시판에 따라 달라지는, 댓글 알림의 경우만.

    private String detail;

    private LocalDateTime createdAt;

    public static NotificationResponseDto from(Notification notification) {
        return NotificationResponseDto.builder()
                .id(notification.getId())
                .receiverId(notification.getReceiver().getId())
                .targetId(notification.getTargetId())
                .senderId(notification.getSender().getId())
                .senderNickname(notification.getSender().getNickname())
                .type(notification.getType())
                .boardType(notification.getBoardType())
                .detail(notification.getDetail())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
