package com.plateform.multiplayer_platform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plateform.multiplayer_platform.Entity.Match;
import com.plateform.multiplayer_platform.Enum.MatchStatus;

public interface MatchRepository extends JpaRepository<Match,Long>{
    boolean existsByPlayer1_IdAndStatusOrPlayer2_IdAndStatus(
        Long player1Id,
        MatchStatus status1,
        Long player2Id,
        MatchStatus status2
    );
}
