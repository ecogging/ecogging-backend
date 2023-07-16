package com.pickupluck.ecogging.domain.notification.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    @GetMapping("/notifications")
    public ResponseEntity getNotifications() {
        return null;
    }

    @DeleteMapping("/notifications/{id}")
    public ResponseEntity readNotification(@PathVariable Long id) {
        return null;
    }
}
