package com.pickupluck.ecogging.domain.notification.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pickupluck.ecogging.domain.notification.entity.Notification;
import com.pickupluck.ecogging.domain.notification.entity.NotificationType;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    public List<Notification> findByIsReadAndIsDeleted(boolean isRead, boolean isDeleted);

    @EntityGraph(attributePaths = "sender")
    public List<Notification> findBySenderId(Long senderId);

    @EntityGraph(attributePaths = "receiver")
    public List<Notification> findByReceiverIdAndIdGreaterThanOrderByCreatedAtDesc(Long receiverId, Long lastReceivedNotificationId);

    public List<Notification> findByType(NotificationType type);
}
