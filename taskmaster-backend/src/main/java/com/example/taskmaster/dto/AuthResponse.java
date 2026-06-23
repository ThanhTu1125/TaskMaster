// 1. AuthResponse.java (TRẢ VỀ KHI LOGIN/REGISTER THÀNH CÔNG)
package com.example.taskmaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String uid;
    private String email;
    private String fullName;
    private String message = "Thành công";
}