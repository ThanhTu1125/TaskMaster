// 6. TaskTagService.java
package com.example.taskmaster.service;

import com.example.taskmaster.dto.TaskTagDTO;
import com.example.taskmaster.model.*;
import com.example.taskmaster.repository.TagRepository;
import com.example.taskmaster.repository.TaskRepository;
import com.example.taskmaster.repository.TaskTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskTagService {

    private final TaskTagRepository taskTagRepository;
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;

    public TaskTagDTO addTagToTask(Long taskId, Long tagId, User user) {
        Task task = taskRepository.findById(taskId)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        Tag tag = tagRepository.findById(tagId)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new IllegalArgumentException("Tag not found"));

        TaskTagId id = new TaskTagId(taskId, tagId);
        if (taskTagRepository.existsById(id)) {
            throw new IllegalArgumentException("Tag already added");
        }

        TaskTag taskTag = new TaskTag();
        taskTag.setId(id);
        taskTag.setTask(task);
        taskTag.setTag(tag);
        taskTagRepository.save(taskTag);

        return new TaskTagDTO(taskId, tagId, task.getTitle(), tag.getName());
    }

    public void removeTagFromTask(Long taskId, Long tagId, User user) {
        TaskTagId id = new TaskTagId(taskId, tagId);
        taskTagRepository.findById(id)
                .filter(tt -> tt.getTask().getUser().getId().equals(user.getId()))
                .ifPresent(taskTagRepository::delete);
    }
}