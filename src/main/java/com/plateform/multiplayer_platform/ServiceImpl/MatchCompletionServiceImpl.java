package com.plateform.multiplayer_platform.ServiceImpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionSynchronization;

import com.plateform.multiplayer_platform.Entity.Match;
import com.plateform.multiplayer_platform.Enum.MatchStatus;
import com.plateform.multiplayer_platform.Repository.MatchRepository;
import com.plateform.multiplayer_platform.Service.MatchCompletionService;
import com.plateform.multiplayer_platform.Service.MatchWebSocketService;

import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchCompletionServiceImpl implements MatchCompletionService{

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchWebSocketService matchWebSocketService;

    @Transactional
    @Override 
    public Match stopMatch(long match_id) {

        Match match = matchRepository.findById(match_id)
            .orElseThrow(() -> new RuntimeException("Match not found"));

        if (match.getStatus() != MatchStatus.RUNNING) {
            return match;
        }

        match.setStatus(MatchStatus.COMPLETED);
        match.setEndedAt(LocalDateTime.now());

        matchRepository.saveAndFlush(match);

        TransactionSynchronizationManager.registerSynchronization(
            new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    matchWebSocketService.notifyWithMatchIsCompleted(match_id);
                }
            }
        );

        return match;
    }
} 
