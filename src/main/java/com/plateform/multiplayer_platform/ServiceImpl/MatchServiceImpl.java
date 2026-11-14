package com.plateform.multiplayer_platform.ServiceImpl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.Entity.Match;
import com.plateform.multiplayer_platform.Enum.MatchStatus;
import com.plateform.multiplayer_platform.Repository.MatchRepository;
import com.plateform.multiplayer_platform.Service.MatchCompletionService;
import com.plateform.multiplayer_platform.Service.MatchService;
import com.plateform.multiplayer_platform.Service.MatchWebSocketService;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.OptimisticLockingFailureException;


@Service
public class MatchServiceImpl implements MatchService {

    @Autowired 
    private MatchRepository matchRepository;

    @Autowired
    private MatchWebSocketService matchWebSocketService;

    @Autowired 
    private MatchCompletionService matchCompletionService;

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
        match.setEndsAt(LocalDateTime.now().plusSeconds(60));
        matchRepository.save(match);
        
        matchScheduler.schedule(
            () -> completeMatchSafely(match_id),
            match.getEndsAt()
                .atZone(ZoneId.systemDefault())
                .toInstant()
        );

        matchWebSocketService.notifyWithMatchIsRunning(match_id);

        return match;
    }


    public void recoverExpiredMatches() {

        List<Match> expiredMatches =
            matchRepository.findByStatusAndEndsAtLessThanEqual(
                MatchStatus.RUNNING,
                LocalDateTime.now()
            );

        for (Match match : expiredMatches) {
            completeMatchSafely(match.getId());
        }
    }

    private void completeMatchSafely(long match_id) {
        try {
            matchCompletionService.stopMatch(match_id);
        } catch (OptimisticLockingFailureException e) {
            System.out.println("Another scheduler already updated this match.");
        }
    }

    public Match findMatchById(long match_id)
    {
        return matchRepository.findById(match_id).orElseThrow(()->new RuntimeException("match not found in match service"));
    }

}