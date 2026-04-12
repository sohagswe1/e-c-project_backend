package com.example.sj.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Complaint {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    private String subject;
    
    @Column(columnDefinition = "text")
    private String message;
    
    private String status;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
