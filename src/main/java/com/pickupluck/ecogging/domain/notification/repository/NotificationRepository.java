package com.pickupluck.ecogging.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pickupluck.ecogging.domain.notification.entity.Notification;
import com.pickupluck.ecogging.domain.notification.entity.NotificationType;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    public List<Notification> findByIsReadAndIsDeleted(boolean isRead, boolean isDeleted);

    @Query("SELECT n FROM Notification n WHERE n.sender.id = ?1")
    public List<Notification> findBySenderId(Long senderId);

    @Query("SELECT n FROM Notification n WHERE n.receiver.id = ?1")
    public List<Notification> findByReceiverId(Long receiverId);

    public List<Notification> findByType(NotificationType type);
}
