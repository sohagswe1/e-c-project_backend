package com.example.sj.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private Integer roleId;
    private LocalDateTime createdAt;
}
