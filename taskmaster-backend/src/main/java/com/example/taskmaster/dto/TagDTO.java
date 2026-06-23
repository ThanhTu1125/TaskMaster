// 6. TagDTO.java (sửa)
package com.example.taskmaster.dto;

import lombok.Data;

@Data
public class TagDTO {
    private Long tagId;
    private String uid;
    private String name;
    private String color;
}