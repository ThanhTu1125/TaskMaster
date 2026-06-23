// 4. UserDTO.java (sửa lại)
package com.example.taskmaster.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String uid;
    private String email;
    private String fullName;
    private String avatar;
}