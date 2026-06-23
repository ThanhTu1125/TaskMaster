// 3. TaskController.java (chỉ lấy task của user hiện tại)
package com.example.taskmaster.controller;

import com.example.taskmaster.dto.TaskDTO;
import com.example.taskmaster.model.Task;
import com.example.taskmaster.model.User;
import com.example.taskmaster.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskDTO> getMyTasks(@AuthenticationPrincipal User user) {
        return taskService.getTasksByUser(user);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long taskId, @AuthenticationPrincipal User user) {
        return taskService.getTaskByIdAndUser(taskId, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO, @AuthenticationPrincipal User user) {
        taskDTO.setTaskId(null); // mới tạo
        TaskDTO created = taskService.createTask(taskDTO, user);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO,
                                              @AuthenticationPrincipal User user) {
        return taskService.updateTask(taskId, taskDTO, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId, @AuthenticationPrincipal User user) {
        boolean deleted = taskService.deleteTask(taskId, user);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}