// 6. NotificationController.java
package com.example.taskmaster.controller;

import com.example.taskmaster.dto.NotificationDTO;
import com.example.taskmaster.model.User;
import com.example.taskmaster.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationDTO> getMyNotifications(@AuthenticationPrincipal User user) {
        return notificationService.getNotificationsByUser(user);
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id, @AuthenticationPrincipal User user) {
        notificationService.markAsRead(id, user);
        return ResponseEntity.ok().build();
    }
}