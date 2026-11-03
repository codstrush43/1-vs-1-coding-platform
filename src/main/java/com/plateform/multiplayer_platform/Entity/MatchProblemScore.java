package com.plateform.multiplayer_platform.Entity;


import com.plateform.multiplayer_platform.Entity.composite_key.MatchProblemScoreId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "match_problem_score")
@IdClass(MatchProblemScoreId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchProblemScore {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @Id
    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private Integer pointsAwarded;
}