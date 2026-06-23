package com.example.taskmaster.repository;

import com.example.taskmaster.model.Tag;
import com.example.taskmaster.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {  // Đổi Integer → Long

    List<Tag> findByUser(User user);

    boolean existsByNameAndUser(String name, User user);
}