package com.pickupluck.ecogging.domain.notification.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.pickupluck.ecogging.domain.notification.dto.NotificationResponseDto;
import com.pickupluck.ecogging.domain.notification.service.NotificationService;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.service.UserService;
import com.pickupluck.ecogging.util.SecurityUtil;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final UserService userService;

    private final NotificationService notificationService;

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationResponseDto>> getNotifications(
            @RequestHeader(value = "Last-Received-Noti-Id", required = false, defaultValue = "0") Long after) {
        log.info(after.toString());
        try {
            String userEmail = SecurityUtil.getCurrentUsername().orElseThrow();
            Long userId = userService.findUserByEmail(userEmail).getId();

            List<NotificationResponseDto> myNotifications = notificationService.getMyNotifications(after);
            return new ResponseEntity<>(myNotifications, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/notifications/{id}")
    public ResponseEntity deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
