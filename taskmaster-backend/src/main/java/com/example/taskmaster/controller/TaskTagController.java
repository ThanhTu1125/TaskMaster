// 7. TaskTagController.java (giữ lại nhưng đổi Long)
package com.example.taskmaster.controller;

import com.example.taskmaster.dto.TaskTagDTO;
import com.example.taskmaster.model.User;
import com.example.taskmaster.service.TaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task-tags")
@RequiredArgsConstructor
public class TaskTagController {

    private final TaskTagService taskTagService;

    @PostMapping
    public ResponseEntity<TaskTagDTO> addTagToTask(@RequestBody TaskTagDTO dto, @AuthenticationPrincipal User user) {
        TaskTagDTO created = taskTagService.addTagToTask(dto.getTaskId(), dto.getTagId(), user);
        return ResponseEntity.status(201).body(created);
    }

    @DeleteMapping("/{taskId}/{tagId}")
    public ResponseEntity<Void> removeTagFromTask(@PathVariable Long taskId, @PathVariable Long tagId,
                                                  @AuthenticationPrincipal User user) {
        taskTagService.removeTagFromTask(taskId, tagId, user);
        return ResponseEntity.noContent().build();
    }
}