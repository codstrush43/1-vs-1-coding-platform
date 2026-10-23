package com.plateform.multiplayer_platform.Entity;

import java.time.LocalDateTime;

import com.plateform.multiplayer_platform.Enum.MatchStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table; 


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player1_id")
    private User player1;

    @ManyToOne
    @JoinColumn(name = "player2_id")
    private User player2;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}