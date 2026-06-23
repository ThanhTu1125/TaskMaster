package com.example.taskmaster.repository;

import com.example.taskmaster.model.Notification;
import com.example.taskmaster.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {  // Đổi Integer → Long

    List<Notification> findByUser(User user);

    List<Notification> findByUserAndIsReadFalse(User user);

    long countByUserAndIsReadFalse(User user);
}