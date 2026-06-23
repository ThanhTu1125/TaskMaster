// 7. ReminderDTO.java (sửa)
package com.example.taskmaster.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReminderDTO {
    private Long reminderId;
    private Long taskId;
    private LocalDateTime reminderTime;
    private String message;
}