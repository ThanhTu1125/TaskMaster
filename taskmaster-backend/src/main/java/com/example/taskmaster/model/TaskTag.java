package com.example.taskmaster.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "task_tags")
@Data
public class TaskTag {

    @EmbeddedId
    private TaskTagId id = new TaskTagId();

    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;
}