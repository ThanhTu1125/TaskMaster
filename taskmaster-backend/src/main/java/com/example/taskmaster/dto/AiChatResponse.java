package com.example.taskmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class AiChatResponse {
    private String reply;
    private List<String> suggestions; // optional (gợi ý câu hỏi nhanh)
}
