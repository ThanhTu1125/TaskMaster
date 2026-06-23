package com.example.taskmaster.repository;

import com.example.taskmaster.model.Task;
import com.example.taskmaster.model.TaskStatus;
import com.example.taskmaster.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> { // Đổi Integer → Long

    List<Task> findByUser(User user);

    // Các method cực kỳ hữu ích sau này (giữ sẵn)
    List<Task> findByUserAndStatus(User user, TaskStatus status);

    List<Task> findByUserAndDueTimeBeforeAndStatusNot(User user, LocalDateTime now, TaskStatus status);

    @Query("SELECT t FROM Task t WHERE t.user = :user ORDER BY t.dueTime ASC")
    List<Task> findAllByUserOrderByDueTime(User user);
}