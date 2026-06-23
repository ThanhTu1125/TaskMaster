package com.example.taskmaster.repository;

import com.example.taskmaster.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> { // Đổi Integer → Long

    List<Reminder> findByTask_TaskId(Long taskId);

    List<Reminder> findByReminderTimeBetween(LocalDateTime start, LocalDateTime end);

    // Dùng cho scheduler nhắc việc sau này
    List<Reminder> findByReminderTimeBeforeAndTask_StatusNot(LocalDateTime now,
            com.example.taskmaster.model.TaskStatus completed);
}