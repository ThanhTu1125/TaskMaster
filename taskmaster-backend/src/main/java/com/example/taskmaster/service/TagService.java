// 3. TagService.java
package com.example.taskmaster.service;

import com.example.taskmaster.dto.TagDTO;
import com.example.taskmaster.model.Tag;
import com.example.taskmaster.model.User;
import com.example.taskmaster.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<TagDTO> getTagsByUser(User user) {
        return tagRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .toList();
    }

    public TagDTO createTag(TagDTO dto, User user) {
        Tag tag = new Tag();
        tag.setUser(user);
        tag.setName(dto.getName());
        tag.setColor(dto.getColor() != null ? dto.getColor() : "#3B82F6");
        tag = tagRepository.save(tag);
        return convertToDTO(tag);
    }

    public boolean deleteTag(Long tagId, User user) {
        return tagRepository.findById(tagId)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .map(tag -> {
                    tagRepository.delete(tag);
                    return true;
                }).orElse(false);
    }

    private TagDTO convertToDTO(Tag tag) {
        TagDTO dto = new TagDTO();
        dto.setTagId(tag.getTagId());
        dto.setUid(tag.getUid());
        dto.setName(tag.getName());
        dto.setColor(tag.getColor());
        return dto;
    }
}