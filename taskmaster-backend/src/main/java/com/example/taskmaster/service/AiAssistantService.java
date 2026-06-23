package com.example.taskmaster.service;

import com.example.taskmaster.dto.AiChatResponse;
import com.example.taskmaster.dto.TaskDTO;
import com.example.taskmaster.model.TaskStatus;
import com.example.taskmaster.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AiAssistantService {

    private final TaskService taskService;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM HH:mm");

    public AiChatResponse chat(User user, String message) {
        String msg = (message == null) ? "" : message.trim();
        if (msg.isBlank()) {
            return new AiChatResponse(
                    "Bạn muốn mình giúp gì về task?\nVí dụ: “Task hôm nay”, “Task tuần này”, “Task quá hạn”, “Task đã hoàn thành”.",
                    quickSug()
            );
        }

        String m = msg.toLowerCase(Locale.ROOT);

        // ✅ Chỉ đọc dữ liệu task của user (KHÔNG tạo/sửa/xoá)
        List<TaskDTO> tasks = taskService.getTasksByUser(user);

        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        if (containsAny(m, "hôm nay", "today")) {
            var list = tasks.stream()
                    .filter(t -> isInDay(t, today))
                    .toList();
            return new AiChatResponse(renderList("📅 Task hôm nay", list), quickSug());
        }

        if (containsAny(m, "tuần", "week")) {
            LocalDate start = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate end = start.plusDays(6);
            var list = tasks.stream()
                    .filter(t -> isInRange(t, start, end))
                    .toList();
            return new AiChatResponse(renderList("🗓️ Task tuần này", list), quickSug());
        }

        if (containsAny(m, "tháng", "month")) {
            YearMonth ym = YearMonth.from(today);
            var list = tasks.stream()
                    .filter(t -> {
                        LocalDate d = pickDate(t);
                        return d != null && YearMonth.from(d).equals(ym);
                    })
                    .toList();
            return new AiChatResponse(renderList("📆 Task tháng này", list), quickSug());
        }

        if (containsAny(m, "năm", "year")) {
            int y = today.getYear();
            var list = tasks.stream()
                    .filter(t -> {
                        LocalDate d = pickDate(t);
                        return d != null && d.getYear() == y;
                    })
                    .toList();
            return new AiChatResponse(renderList("📌 Task năm nay", list), quickSug());
        }

        if (containsAny(m, "quá hạn", "overdue")) {
            var list = tasks.stream()
                    .filter(t -> t.getDueTime() != null
                            && t.getDueTime().isBefore(now)
                            && t.getStatus() != TaskStatus.COMPLETED)
                    .toList();
            return new AiChatResponse(renderList("⏰ Task quá hạn", list), quickSug());
        }

        if (containsAny(m, "hoàn thành", "completed", "done")) {
            var list = tasks.stream()
                    .filter(t -> t.getStatus() == TaskStatus.COMPLETED)
                    .toList();
            return new AiChatResponse(renderList("✅ Task đã hoàn thành", list), quickSug());
        }

        // fallback (không nhắc tạo task nữa)
        return new AiChatResponse(
                "Mình có thể giúp bạn tổng quát danh sách task.\n" +
                        "Bạn thử hỏi: “Task hôm nay”, “Task tuần này”, “Task quá hạn”, “Task đã hoàn thành”.",
                quickSug()
        );
    }

    private static boolean containsAny(String s, String... keys) {
        for (String k : keys) if (s.contains(k)) return true;
        return false;
    }

    private static List<String> quickSug() {
        return List.of("Task hôm nay", "Task tuần này", "Task quá hạn", "Task đã hoàn thành");
    }

    /** ưu tiên dueTime, nếu null thì startTime */
    private static LocalDate pickDate(TaskDTO t) {
        if (t.getDueTime() != null) return t.getDueTime().toLocalDate();
        if (t.getStartTime() != null) return t.getStartTime().toLocalDate();
        return null;
    }

    private static boolean isInDay(TaskDTO t, LocalDate day) {
        LocalDate d = pickDate(t);
        return d != null && d.equals(day);
    }

    private static boolean isInRange(TaskDTO t, LocalDate start, LocalDate end) {
        LocalDate d = pickDate(t);
        return d != null && (!d.isBefore(start) && !d.isAfter(end));
    }

    private String renderList(String title, List<TaskDTO> list) {
        if (list.isEmpty()) {
            return title + "\nKhông có task nào phù hợp.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("\n");
        list.stream().limit(12).forEach(t -> {
            String due = (t.getDueTime() != null) ? " (due " + t.getDueTime().format(DTF) + ")" : "";
            sb.append("- [").append(t.getStatus()).append("] ").append(t.getTitle()).append(due).append("\n");
        });
        if (list.size() > 12) sb.append("… còn ").append(list.size() - 12).append(" task nữa.\n");
        return sb.toString();
    }
}
