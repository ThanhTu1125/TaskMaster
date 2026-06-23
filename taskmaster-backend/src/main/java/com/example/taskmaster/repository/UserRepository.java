package com.example.taskmaster.repository;

import com.example.taskmaster.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {  // Đổi từ String → Long

    Optional<User> findByEmail(String email);

    Optional<User> findByUid(String uid);
}