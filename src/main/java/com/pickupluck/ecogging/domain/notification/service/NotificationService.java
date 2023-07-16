package com.pickupluck.ecogging.domain.notification.service;

import com.pickupluck.ecogging.domain.notification.dto.NotificationResponseDto;
import com.pickupluck.ecogging.domain.notification.dto.NotificationSaveDto;
import com.pickupluck.ecogging.domain.notification.entity.Notification;
import com.pickupluck.ecogging.domain.user.entity.User;

import java.security.InvalidParameterException;
import java.util.List;

public interface NotificationService {

    public List<NotificationResponseDto> getMyNotifications(Long receiverId);

    public void createNotification(NotificationSaveDto notificationSaveDto);

    public void deleteNotification(Long id);
}
