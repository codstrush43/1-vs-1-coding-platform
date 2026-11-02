package com.plateform.multiplayer_platform.ServiceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.Entity.PlayerScore;
import com.plateform.multiplayer_platform.Entity.composite_key.PlayerScoreId;
import com.plateform.multiplayer_platform.Repository.PlayerScoreRepository;
import com.plateform.multiplayer_platform.Service.PlayerScoreService;

@Service 
public class PlayerScoreServiceImpl implements PlayerScoreService{  

    @Autowired  
    private PlayerScoreRepository playerScoreRepository;

    @Override
    public void savePlayerScore(PlayerScore playerScore) {
        
        playerScoreRepository.save(playerScore);
        
    }

    @Override
    public void updatePLayerScore(long user_id,long match_id) {
        PlayerScoreId playerScoreId = new PlayerScoreId(user_id,match_id);

        PlayerScore playerScore =playerScoreRepository.findById(playerScoreId)
                                    .orElseThrow(()->new RuntimeException("PlayerScore not found while updating the score!"));
        playerScore.setScore(playerScore.getScore()+100);
        playerScoreRepository.save(playerScore);
    }

    @Override 
    public PlayerScore findById(long user_id,long match_id)
    {
        PlayerScoreId playerScoreId=new PlayerScoreId(user_id,match_id);
        return playerScoreRepository.findById(playerScoreId)
                                    .orElseThrow(()->new RuntimeException("PlayerScore not found while finding the score!"));
    }

    @Override 
    public List<PlayerScore> findByMatchId(long match_id)
    {
        return playerScoreRepository.findByMatchId(match_id);
    }

}
