// 9. TaskTagDTO.java (giữ lại nhưng sửa kiểu Long)
package com.example.taskmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskTagDTO {
    private Long taskId;
    private Long tagId;
    private String taskTitle;
    private String tagName;
}