package com.plateform.multiplayer_platform.Service;

import java.util.List;

import com.plateform.multiplayer_platform.Entity.PlayerScore;

public interface PlayerScoreService {
    void savePlayerScore(PlayerScore playerScore);
    void updatePLayerScore(long user_id,long match_id,long problem_id);
    PlayerScore findById(long user_id,long match_id);
    List<PlayerScore> findByMatchId(long match_id);
    public String getWinnerStatus(long match_id);
}
