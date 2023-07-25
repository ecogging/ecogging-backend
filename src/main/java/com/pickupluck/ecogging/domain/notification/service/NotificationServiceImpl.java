package com.pickupluck.ecogging.domain.notification.service;

import java.security.InvalidParameterException;
import java.util.List;

import com.pickupluck.ecogging.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.pickupluck.ecogging.domain.notification.dto.NotificationResponseDto;
import com.pickupluck.ecogging.domain.notification.dto.NotificationSaveDto;
import com.pickupluck.ecogging.domain.notification.entity.Notification;
import com.pickupluck.ecogging.domain.notification.repository.NotificationRepository;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public List<NotificationResponseDto> getMyNotifications(Long lastReceivedNotificationId) throws Exception {

        User user = SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findByEmail)
                .orElseThrow(() -> new Exception("Member not found"));

        List<Notification> notifications = notificationRepository
                .findByReceiverIdAndIdGreaterThanOrderByCreatedAtDesc(user.getId(), lastReceivedNotificationId);

        return notifications.stream()
                .map(notification -> NotificationResponseDto.from(notification))
                .toList();
    }

    @Override
    @Transactional
    public void createNotification(NotificationSaveDto notificationSaveDto) {

        User sender = userRepository.findById(notificationSaveDto.getSenderId())
                .orElseThrow(() -> new InvalidParameterException("No User(Sender) for Given Id"));
        User receiver = userRepository.findById(notificationSaveDto.getReceiverId())
                .orElseThrow(() -> new InvalidParameterException("No User(Receiver) for Given Id"));

        if (sender.equals(receiver)) {
            log.info("알림: 수신자와 발신자 같을 수 없음");
            return;
        }

        Notification notification = Notification.builder()
                                        .sender(sender)
                                        .receiver(receiver)
                                        .type(notificationSaveDto.getType())
                                        .targetId(notificationSaveDto.getTargetId())
                                        .boardType(notificationSaveDto.getBoardType())
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
