package com.plateform.multiplayer_platform.Service;

import com.plateform.multiplayer_platform.DTOs.MatchWebSocketDTO;

public interface MatchWebSocketService {
    void updateStatus(MatchWebSocketDTO matchWebSocketDTO);
    void notifyWithMatchIsRunning(long match_id);
}
