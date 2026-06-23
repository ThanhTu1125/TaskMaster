// 5. ReminderController.java
package com.example.taskmaster.controller;

import com.example.taskmaster.dto.ReminderDTO;
import com.example.taskmaster.model.User;
import com.example.taskmaster.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @GetMapping("/upcoming")
    public List<ReminderDTO> getUpcomingReminders(@AuthenticationPrincipal User user) {
        return reminderService.getUpcomingRemindersForUser(user);
    }

    @PostMapping
    public ResponseEntity<ReminderDTO> createReminder(@RequestBody ReminderDTO dto, @AuthenticationPrincipal User user) {
        ReminderDTO created = reminderService.createReminder(dto, user);
        return ResponseEntity.status(201).body(created);
    }
}