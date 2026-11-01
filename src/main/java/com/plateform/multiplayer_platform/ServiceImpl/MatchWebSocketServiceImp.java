package com.plateform.multiplayer_platform.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.DTOs.MatchWebSocketDTO;
import com.plateform.multiplayer_platform.Service.MatchWebSocketService;


@Service 
public class MatchWebSocketServiceImp implements MatchWebSocketService{

    @Autowired 
    private SimpMessagingTemplate messagingTemplate;

    @Override 
    public void updateStatus(MatchWebSocketDTO matchWebSocketDTO){
        messagingTemplate.convertAndSend("/topic/match/"+matchWebSocketDTO.getMatch_id(),+ matchWebSocketDTO.getPlayer_id()+" - "+matchWebSocketDTO.getVerdict());
    }

    @Override 
    public void notifyWithMatchIsRunning(long match_id)
    {
        messagingTemplate.convertAndSend(
                "/topic/match/" + match_id,
                "MATCH_STARTED"
        );
    }

}
