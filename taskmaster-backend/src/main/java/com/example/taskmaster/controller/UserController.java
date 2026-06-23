//// 2. UserController.java (lấy user hiện tại từ JWT)
//package com.example.taskmaster.controller;
//
//import com.example.taskmaster.dto.UserDTO;
//import com.example.taskmaster.model.User;
//import com.example.taskmaster.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/users")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserService userService;
//
//    @GetMapping("/me")
//    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal User currentUser) {
//        UserDTO dto = new UserDTO();
//        dto.setId(currentUser.getId());
//        dto.setUid(currentUser.getUid());
//        dto.setEmail(currentUser.getEmail());
//        dto.setFullName(currentUser.getFullName());
//        dto.setAvatar(currentUser.getAvatar());
//        return ResponseEntity.ok(dto);
//    }
//}
package com.example.taskmaster.controller;

import com.example.taskmaster.dto.*;
import com.example.taskmaster.model.User;
import com.example.taskmaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(toDto(currentUser));
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateMe(
            @AuthenticationPrincipal User currentUser,
            @RequestBody UpdateProfileRequest req
    ) {
        try {
            User updated = userService.updateProfile(currentUser, req);
            return ResponseEntity.ok(toDto(updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/me/change-password")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal User currentUser,
            @RequestBody ChangePasswordRequest req
    ) {
        try {
            userService.changePassword(currentUser, req);
            return ResponseEntity.ok(new MessageResponse("Đổi mật khẩu thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    private UserDTO toDto(User u) {
        UserDTO dto = new UserDTO();
        dto.setId(u.getId());
        dto.setUid(u.getUid());
        dto.setEmail(u.getEmail());
        dto.setFullName(u.getFullName());
        dto.setAvatar(u.getAvatar());
        return dto;
    }
}
