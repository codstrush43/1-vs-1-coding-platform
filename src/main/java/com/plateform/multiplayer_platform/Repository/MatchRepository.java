package com.plateform.multiplayer_platform.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.plateform.multiplayer_platform.Entity.Match;
import com.plateform.multiplayer_platform.Enum.MatchStatus;

public interface MatchRepository extends JpaRepository<Match,Long>{
    @Query("""
            SELECT COUNT(m)
            FROM Match m
            WHERE (m.player1.id = :userId OR m.player2.id = :userId) AND (m.status IN :statuses)
        """)
    long countActiveMatches(
        @Param("userId") Long userId,
        @Param("statuses") List<MatchStatus> statuses
    );

    Optional<Match> findById(long match_id);

}
