package com.plateform.multiplayer_platform.Entity;

import jakarta.persistence.Id;

import com.plateform.multiplayer_platform.Entity.composite_key.PlayerScoreId;

import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "player_score")
@IdClass(PlayerScoreId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerScore {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    private Integer score;
}
