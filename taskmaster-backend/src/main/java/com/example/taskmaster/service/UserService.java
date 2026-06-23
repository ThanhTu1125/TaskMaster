package com.example.taskmaster.service;

import com.example.taskmaster.dto.ChangePasswordRequest;
import com.example.taskmaster.dto.UpdateProfileRequest;
import com.example.taskmaster.model.User;
import com.example.taskmaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User getUserByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    public User updateProfile(User currentUser, UpdateProfileRequest req) {
        if (req == null) return currentUser;

        if (req.getFullName() != null) {
            String fn = req.getFullName().trim();
            if (!fn.isBlank()) currentUser.setFullName(fn);
        }

        if (req.getAvatar() != null) {
            String av = req.getAvatar().trim();
            currentUser.setAvatar(av.isBlank() ? null : av);
        }

        return userRepository.save(currentUser);
    }

    public void changePassword(User currentUser, ChangePasswordRequest req) {
        if (req == null || req.getOldPassword() == null || req.getNewPassword() == null) {
            throw new IllegalArgumentException("Thiếu dữ liệu đổi mật khẩu.");
        }

        if (!passwordEncoder.matches(req.getOldPassword(), currentUser.getPassword())) {
            throw new IllegalArgumentException("Mật khẩu hiện tại không đúng.");
        }

        String newPass = req.getNewPassword().trim();
        if (newPass.length() < 6) {
            throw new IllegalArgumentException("Mật khẩu mới tối thiểu 6 ký tự.");
        }

        currentUser.setPassword(passwordEncoder.encode(newPass));
        userRepository.save(currentUser);
    }
}
