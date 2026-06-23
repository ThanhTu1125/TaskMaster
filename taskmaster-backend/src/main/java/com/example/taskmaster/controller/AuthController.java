// 1. AuthController.java (HOÀN HẢO – trả JWT + phân quyền tự động)
package com.example.taskmaster.controller;

import com.example.taskmaster.dto.AuthResponse;
import com.example.taskmaster.dto.LoginRequest;
import com.example.taskmaster.dto.RegisterRequest;
import com.example.taskmaster.model.User;
import com.example.taskmaster.security.JwtTokenProvider;
import com.example.taskmaster.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, null, null, null, "Email đã tồn tại"));
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // nên có:
        // user.setUid(UUID.randomUUID().toString());
        // user.setRole(Role.USER);

        User savedUser = userService.createUser(user);

        String jwt = jwtTokenProvider.generateTokenFromEmail(savedUser.getEmail());

        return ResponseEntity.status(201)
                .body(new AuthResponse(
                        jwt,
                        savedUser.getUid(),
                        savedUser.getEmail(),
                        savedUser.getFullName(),
                        "Đăng ký thành công"
                ));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);

        User user = userService.getUserByEmailOrThrow(request.getEmail());
        return ResponseEntity.ok(new AuthResponse(jwt, user.getUid(), user.getEmail(), user.getFullName(), "Đăng nhập thành công"));
    }

}