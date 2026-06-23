// 5. TaskDTO.java (sửa + bổ sung)
package com.example.taskmaster.dto;

import com.example.taskmaster.model.Priority;
import com.example.taskmaster.model.TaskStatus;
import com.example.taskmaster.model.TaskType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDTO {
    private Long taskId;
    private String uid;
    private String title;
    private String description;
    private TaskType type;
    private Priority priority;
    private TaskStatus status;
    private LocalDateTime startTime;
    private LocalDateTime dueTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> tagNames;        // trả về tên tag
    private List<String> tagColor;        // trả về tên tag
    private List<Long> tagIds;            // để frontend xử lý
}