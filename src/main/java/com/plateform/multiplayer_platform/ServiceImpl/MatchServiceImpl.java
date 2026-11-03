package com.plateform.multiplayer_platform.ServiceImpl;

import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
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

    @Autowired 
    @Qualifier("getScheduler") 
    private TaskScheduler matchScheduler;

    @Override 
    public Match createMatch(Match match) {
        match.setStatus(MatchStatus.WAITING);
        return matchRepository.save(match);
    }

    @Transactional 
    @Override 
    public Match startMatch(long match_id)
    {
        Match match = findMatchById(match_id);
        if (match.getStatus() != MatchStatus.READY) {
            return match;
        }
        
        match.setStatus(MatchStatus.RUNNING);
        match.setStartedAt(LocalDateTime.now());
        matchRepository.save(match);
        
        matchScheduler.schedule(()->{stopMatch(match_id);}, Instant.now().plusSeconds(60));

        matchWebSocketService.notifyWithMatchIsRunning(match_id);

        return match;
    }

    @Transactional 
    @Override 
    public Match stopMatch(long match_id)
    {
        Match match = findMatchById(match_id);

        if(match.getStatus()==MatchStatus.COMPLETED)
        {
            return match;
        }

        match.setStatus(MatchStatus.COMPLETED);
        match.setEndedAt(LocalDateTime.now());
        matchRepository.save(match);

        matchWebSocketService.notifyWithMatchIsCompleted(match_id);
        return match;
    }

    public Match findMatchById(long match_id)
    {
        return matchRepository.findById(match_id).orElseThrow(()->new RuntimeException("match not found in match service"));
    }

}