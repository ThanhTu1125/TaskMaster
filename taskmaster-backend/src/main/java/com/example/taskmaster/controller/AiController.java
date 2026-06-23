package com.example.taskmaster.controller;

import com.example.taskmaster.dto.AiChatRequest;
import com.example.taskmaster.dto.AiChatResponse;
import com.example.taskmaster.dto.MessageResponse;
import com.example.taskmaster.model.User;
import com.example.taskmaster.service.AiAssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiAssistantService aiAssistantService;

    @PostMapping("/chat")
    public ResponseEntity<?> chat(
            @AuthenticationPrincipal User currentUser,
            @RequestBody AiChatRequest req
    ) {
        try {
            AiChatResponse res = aiAssistantService.chat(currentUser, req.getMessage());
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
