// 4. TagController.java
package com.example.taskmaster.controller;

import com.example.taskmaster.dto.TagDTO;
import com.example.taskmaster.model.User;
import com.example.taskmaster.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public List<TagDTO> getMyTags(@AuthenticationPrincipal User user) {
        return tagService.getTagsByUser(user);
    }

    @PostMapping
    public ResponseEntity<TagDTO> createTag(@RequestBody TagDTO tagDTO, @AuthenticationPrincipal User user) {
        TagDTO created = tagService.createTag(tagDTO, user);
        return ResponseEntity.status(201).body(created);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long tagId, @AuthenticationPrincipal User user) {
        boolean deleted = tagService.deleteTag(tagId, user);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}