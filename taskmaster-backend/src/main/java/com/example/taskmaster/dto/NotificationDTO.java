package com.example.taskmaster.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Long notificationId;
    private String title;
    private String message;
    private Boolean isRead;           // ← Đổi thành Boolean
    private LocalDateTime createdAt;
    private Long relatedTaskId;
}