package com.example.taskmaster.dto;

import lombok.Data;

@Data
public class AiChatMessage {
    private String role;     // "user" | "assistant"
    private String content;
}
