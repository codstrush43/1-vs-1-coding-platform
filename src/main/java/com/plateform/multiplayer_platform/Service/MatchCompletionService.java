package com.plateform.multiplayer_platform.Service;

import com.plateform.multiplayer_platform.Entity.Match;

public interface MatchCompletionService {
    public Match stopMatch(long match_id);
}
