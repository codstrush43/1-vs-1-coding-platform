package com.plateform.multiplayer_platform.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "problems")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Problem {

    @Id
    private Long id;

    private String title;

    private String description;

    private String difficulty;

    private String inputFormat;

    private String outputFormat;

    private String constraints;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}