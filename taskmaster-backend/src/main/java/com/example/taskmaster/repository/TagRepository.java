package com.example.taskmaster.repository;

import com.example.taskmaster.model.Tag;
import com.example.taskmaster.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> { // Đổi Integer → Long

    List<Tag> findByUser(User user);

    boolean existsByNameAndUser(String name, User user);
}