package com.plateform.multiplayer_platform.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.plateform.multiplayer_platform.Entity.MatchQueue;


public interface MatchQueueRepository extends JpaRepository<MatchQueue,Long> {
    Optional<MatchQueue> findByUserId(long userId);

    
    @Query(value = """
        SELECT *
        FROM match_queue
        ORDER BY joined_at ASC
        LIMIT 2
        """, nativeQuery = true)
    List<MatchQueue> findFirstTwoPlayers();

    @Query(value= """
        SELECT id
        FROM matchmaking_lock
        WHERE id = 1
        FOR UPDATE        
        """ , nativeQuery = true)
        Integer lockMatchmaking();

}
