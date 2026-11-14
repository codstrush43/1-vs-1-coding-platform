package com.plateform.multiplayer_platform.Service;

import org.springframework.messaging.handler.annotation.DestinationVariable;

import com.plateform.multiplayer_platform.DTOs.MatchWebSocketDTO;

public interface MatchWebSocketService {
    void updateStatus(MatchWebSocketDTO matchWebSocketDTO);
    void notifyWithMatchIsRunning(long match_id);
    void notifyWithMatchIsCompleted(long match_id);
    void notifyMatchReady(long matchId);
}
