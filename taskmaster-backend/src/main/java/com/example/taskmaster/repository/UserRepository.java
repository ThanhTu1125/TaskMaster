package com.example.taskmaster.repository;

import com.example.taskmaster.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // Đổi từ String → Long

    Optional<User> findByEmail(String email);

    Optional<User> findByUid(String uid);
}