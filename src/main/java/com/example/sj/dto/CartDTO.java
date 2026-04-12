package com.example.sj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {
    private Integer id;
    private Integer userId;
}
