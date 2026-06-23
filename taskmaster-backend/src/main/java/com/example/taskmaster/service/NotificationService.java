// 4. NotificationService.java
package com.example.taskmaster.service;

import com.example.taskmaster.dto.NotificationDTO;
import com.example.taskmaster.model.Notification;
import com.example.taskmaster.model.User;
import com.example.taskmaster.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<NotificationDTO> getNotificationsByUser(User user) {
        return notificationRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public void markAsRead(Long id, User user) {
        notificationRepository.findById(id)
                .filter(n -> n.getUser().getId().equals(user.getId()))
                .ifPresent(n -> {
                    n.setIsRead(true);
                    notificationRepository.save(n);
                });
    }

    private NotificationDTO convertToDTO(Notification n) {
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationId(n.getNotificationId());
        dto.setTitle(n.getTitle());
        dto.setMessage(n.getMessage());
        dto.setIsRead(n.getIsRead());
        dto.setCreatedAt(n.getCreatedAt());
        dto.setRelatedTaskId(n.getRelatedTask() != null ? n.getRelatedTask().getTaskId() : null);
        return dto;
    }
}