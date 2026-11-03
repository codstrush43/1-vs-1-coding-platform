package com.plateform.multiplayer_platform.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.Entity.MatchProblemScore;
import com.plateform.multiplayer_platform.Entity.PlayerScore;
import com.plateform.multiplayer_platform.Entity.composite_key.PlayerScoreId;
import com.plateform.multiplayer_platform.Repository.PlayerScoreRepository;
import com.plateform.multiplayer_platform.Service.MatchProblemScoreService;
import com.plateform.multiplayer_platform.Service.PlayerScoreService;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional 
public class PlayerScoreServiceImpl implements PlayerScoreService{  

    @Autowired  
    private PlayerScoreRepository playerScoreRepository;

    @Autowired 
    private MatchProblemScoreService matchProblemScoreService;

    @Override
    public void savePlayerScore(PlayerScore playerScore) {
        
        playerScoreRepository.save(playerScore);
        
    }

    @Override
    public void updatePLayerScore(long user_id,long match_id,long problem_id) {
        PlayerScoreId playerScoreId = new PlayerScoreId(user_id,match_id);
        MatchProblemScore matchProblemScore = matchProblemScoreService.findById(user_id, match_id, problem_id);

        PlayerScore playerScore =playerScoreRepository.findById(playerScoreId)
                                    .orElseThrow(()->new RuntimeException("PlayerScore not found while updating the score!"));
        playerScore.setScore(playerScore.getScore()+matchProblemScore.getPointsAwarded());
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

    @Override
    public String getWinnerStatus(long match_id)
    {
        List<PlayerScore> playerScores =findByMatchId(match_id); 

        String result="Draw";

        if(playerScores != null && playerScores.size() == 2)
        {
            if(playerScores.get(0).getScore() > playerScores.get(1).getScore())
            {
                result="Winner : "+playerScores.get(0).getUser().getId();
            }
            else if(playerScores.get(0).getScore() < playerScores.get(1).getScore())
            {
                result="Winner : "+playerScores.get(1).getUser().getId();
            }
        }
        return result;
    }
}
