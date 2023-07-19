package com.pickupluck.ecogging.domain.notification.service;

import java.security.InvalidParameterException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.pickupluck.ecogging.domain.notification.dto.NotificationResponseDto;
import com.pickupluck.ecogging.domain.notification.dto.NotificationSaveDto;
import com.pickupluck.ecogging.domain.notification.entity.Notification;
import com.pickupluck.ecogging.domain.notification.repository.NotificationRepository;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import com.pickupluck.ecogging.util.SecurityUtil;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;

    private final NotificationRepository notificationRepository;

    private NotificationResponseDto notificationToResponse(Notification notification) {
        return NotificationResponseDto.builder()
                .id(notification.getId())
                .targetId(notification.getTargetId())
                .senderId(notification.getSender().getId())
                .senderNickname(notification.getSender().getNickname())
                .type(notification.getType())
                .detail(notification.getDetail())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    @Transactional
    public List<NotificationResponseDto> getMyNotifications(Long lastReceivedNotificationId) {
        String userEmail = SecurityUtil
                .getCurrentUsername()
                .orElseThrow(() -> new IllegalStateException("No user in security context"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("No user for given email"));

        List<Notification> notifications = notificationRepository
                .findByReceiverIdAndIdGreaterThanOOrderByCreatedAtDesc(user.getId(), lastReceivedNotificationId);

        return notifications.stream()
                .map(notification -> notificationToResponse(notification))
                .toList();
    }

    public void createNotification(NotificationSaveDto notificationSaveDto) {
        User sender = userRepository.findById(notificationSaveDto.getSenderId())
                .orElseThrow(() -> new InvalidParameterException("No User(Sender) for Given Id"));
        User receiver = userRepository.findById(notificationSaveDto.getReceiverId())
                .orElseThrow(() -> new InvalidParameterException("No User(Receiver) for Given Id"));


        Notification notification = Notification.builder()
                                        .sender(sender)
                                        .receiver(receiver)
                                        .type(notificationSaveDto.getType())
                                        .targetId(notificationSaveDto.getTargetId())
                                        .detail(notificationSaveDto.getDetail())
                                        .isRead(false)
                                        .isDeleted(false)
                                        .build();

        notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

}
