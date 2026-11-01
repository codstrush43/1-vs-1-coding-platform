package com.plateform.multiplayer_platform.ServiceImpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.Entity.Match;
import com.plateform.multiplayer_platform.Enum.MatchStatus;
import com.plateform.multiplayer_platform.Repository.MatchRepository;
import com.plateform.multiplayer_platform.Service.MatchService;
import com.plateform.multiplayer_platform.Service.MatchWebSocketService;

import org.springframework.transaction.annotation.Transactional;


@Service
public class MatchServiceImpl implements MatchService {

    @Autowired 
    private MatchRepository matchRepository;

    @Autowired
    private MatchWebSocketService matchWebSocketService;

    @Override 
    public Match createMatch(Match match) {
        match.setStatus(MatchStatus.WAITING);
        return matchRepository.save(match);
    }

    @Transactional 
    @Override 
    public Match startMatch(long match_id)
    {
        Match match = matchRepository.findById(match_id).orElseThrow(()->new RuntimeException("match not found when we can try to stating it"));
        if (match.getStatus() != MatchStatus.READY) {
            return match;
        }
        
        match.setStatus(MatchStatus.RUNNING);
        match.setStartedAt(LocalDateTime.now());
        matchRepository.save(match);

        matchWebSocketService.notifyWithMatchIsRunning(match_id);

        return match;
    }

}