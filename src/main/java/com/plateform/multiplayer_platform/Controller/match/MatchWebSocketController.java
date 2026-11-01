package com.plateform.multiplayer_platform.Controller.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller 
public class MatchWebSocketController {

    @Autowired 
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/match/{matchId}")
    public void notifyMatchReady(@DestinationVariable Long matchId) {
    messagingTemplate.convertAndSend("/topic/match/" + matchId,"MATCH_READY");
}
}
