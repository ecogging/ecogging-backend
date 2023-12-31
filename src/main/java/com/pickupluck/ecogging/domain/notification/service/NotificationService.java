package com.pickupluck.ecogging.domain.notification.service;

import com.pickupluck.ecogging.domain.notification.dto.NotificationResponseDto;
import com.pickupluck.ecogging.domain.notification.dto.NotificationSaveDto;

import java.util.List;

public interface NotificationService {

    public List<NotificationResponseDto> getMyNotifications(Long lastReceivedNotificationId) throws Exception;

    public void createNotification(NotificationSaveDto notificationSaveDto);

    public void deleteNotification(Long id);

    public void readNotification(Long id) throws Exception ;
}
