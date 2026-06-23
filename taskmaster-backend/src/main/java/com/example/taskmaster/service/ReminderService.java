// 5. ReminderService.java
package com.example.taskmaster.service;

import com.example.taskmaster.dto.ReminderDTO;
import com.example.taskmaster.model.Reminder;
import com.example.taskmaster.model.Task;
import com.example.taskmaster.model.User;
import com.example.taskmaster.repository.ReminderRepository;
import com.example.taskmaster.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final TaskRepository taskRepository;

    public List<ReminderDTO> getUpcomingRemindersForUser(User user) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in24h = now.plusDays(1);
        return reminderRepository.findByReminderTimeBetween(now, in24h).stream()
                .filter(r -> r.getTask().getUser().getId().equals(user.getId()))
                .map(this::convertToDTO)
                .toList();
    }

    public ReminderDTO createReminder(ReminderDTO dto, User user) {
        Task task = taskRepository.findById(dto.getTaskId())
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new IllegalArgumentException("Task not found or not owned"));

        Reminder reminder = new Reminder();
        reminder.setTask(task);
        reminder.setReminderTime(dto.getReminderTime());
        reminder.setMessage(dto.getMessage());
        reminder = reminderRepository.save(reminder);
        return convertToDTO(reminder);
    }

    private ReminderDTO convertToDTO(Reminder r) {
        ReminderDTO dto = new ReminderDTO();
        dto.setReminderId(r.getReminderId());
        dto.setTaskId(r.getTask().getTaskId());
        dto.setReminderTime(r.getReminderTime());
        dto.setMessage(r.getMessage());
        return dto;
    }
}