// 2. TaskService.java (HOÀN HẢO – mapping DTO + tag)
package com.example.taskmaster.service;

import com.example.taskmaster.dto.TaskDTO;
import com.example.taskmaster.model.*;
import com.example.taskmaster.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskDTO> getTasksByUser(User user) {
        return taskRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public Optional<TaskDTO> getTaskByIdAndUser(Long taskId, User user) {
        return taskRepository.findById(taskId)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .map(this::convertToDTO);
    }

    public TaskDTO createTask(TaskDTO dto, User user) {
        Task task = new Task();
        task.setUser(user);
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setType(dto.getType());
        task.setPriority(dto.getPriority() != null ? dto.getPriority() : Priority.MEDIUM);
        task.setStatus(dto.getStatus() != null ? dto.getStatus() : TaskStatus.PENDING);
        task.setStartTime(dto.getStartTime());
        task.setDueTime(dto.getDueTime());

        task = taskRepository.save(task);
        return convertToDTO(task);
    }

    public Optional<TaskDTO> updateTask(Long taskId, TaskDTO dto, User user) {
        return taskRepository.findById(taskId)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .map(task -> {
                    task.setTitle(dto.getTitle());
                    task.setDescription(dto.getDescription());
                    task.setType(dto.getType());
                    task.setPriority(dto.getPriority());
                    task.setStatus(dto.getStatus());
                    task.setStartTime(dto.getStartTime());
                    task.setDueTime(dto.getDueTime());
                    task.setUpdatedAt(LocalDateTime.now());
                    return convertToDTO(taskRepository.save(task));
                });
    }

    public boolean deleteTask(Long taskId, User user) {
        return taskRepository.findById(taskId)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .map(task -> {
                    taskRepository.delete(task);
                    return true;
                }).orElse(false);
    }

    private TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setTaskId(task.getTaskId());
        dto.setUid(task.getUid());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setType(task.getType());
        dto.setPriority(task.getPriority());
        dto.setStatus(task.getStatus());
        dto.setStartTime(task.getStartTime());
        dto.setDueTime(task.getDueTime());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setTagNames(task.getTags().stream().map(Tag::getName).toList());
        dto.setTagColor(task.getTags().stream().map(Tag::getColor).toList());
        dto.setTagIds(task.getTags().stream().map(Tag::getTagId).toList());
        return dto;
    }
}