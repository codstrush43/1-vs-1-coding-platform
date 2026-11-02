package com.plateform.multiplayer_platform.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plateform.multiplayer_platform.Entity.PlayerScore;
import com.plateform.multiplayer_platform.Entity.composite_key.PlayerScoreId;
import java.util.List;


public interface PlayerScoreRepository extends JpaRepository<PlayerScore,PlayerScoreId> {

    List<PlayerScore> findByMatchId(long match_id);

}
