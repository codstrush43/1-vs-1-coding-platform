package com.plateform.multiplayer_platform.ServiceImpl;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.DTOs.MatchWebSocketDTO;
import com.plateform.multiplayer_platform.Entity.PlayerScore;
import com.plateform.multiplayer_platform.Service.MatchWebSocketService;
import com.plateform.multiplayer_platform.Service.PlayerScoreService;


@Service 
public class MatchWebSocketServiceImp implements MatchWebSocketService{

    @Autowired 
    private SimpMessagingTemplate messagingTemplate;

    @Autowired 
    private PlayerScoreService playerScoreService;

    @Override 
    public void updateStatus(MatchWebSocketDTO matchWebSocketDTO){
        messagingTemplate.convertAndSend("/topic/match/"+matchWebSocketDTO.getMatch_id()+"/code",matchWebSocketDTO.getPlayer_id()+" - "+matchWebSocketDTO.getVerdict());
    }

    @Override 
    public void notifyWithMatchIsRunning(long match_id)
    {
        messagingTemplate.convertAndSend(
                "/topic/match/" + match_id,
                "MATCH_STARTED"
        );
    }

    @Override 
    public void notifyWithMatchIsCompleted(long match_id)
    {

        List<PlayerScore> playerScores = playerScoreService.findByMatchId(match_id); 

        String resulet="Draw";

        if(playerScores != null && playerScores.size() == 2)
        {
            if(playerScores.get(0).getScore() > playerScores.get(1).getScore())
            {
                resulet="Winner : "+playerScores.get(0).getUser().getId();
            }
            else if(playerScores.get(0).getScore() < playerScores.get(1).getScore())
            {
                resulet="Winner : "+playerScores.get(1).getUser().getId();
            }
        }

        messagingTemplate.convertAndSend(
                "/topic/match/" + match_id,
                resulet
        );

        messagingTemplate.convertAndSend(
                "/topic/match/" + match_id,
                "MATCH_COMPLETED"
        );
    }

}
