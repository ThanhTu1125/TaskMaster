package com.example.taskmaster.repository;

import com.example.taskmaster.model.TaskTag;
import com.example.taskmaster.model.TaskTagId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTagId> {

    // Đổi Integer → Long
    List<TaskTag> findByTask_TaskId(Long taskId);

    List<TaskTag> findByTag_TagId(Long tagId);

    void deleteByTask_TaskId(Long taskId);
}