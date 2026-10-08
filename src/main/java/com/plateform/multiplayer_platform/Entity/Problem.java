package com.plateform.multiplayer_platform.Entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
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