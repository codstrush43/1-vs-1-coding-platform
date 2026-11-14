package com.plateform.multiplayer_platform.Scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.plateform.multiplayer_platform.Service.MatchService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MatchRecoveryScheduler {

    private final MatchService matchService;

    @Scheduled(fixedDelay = 30000)
    public void recoverMatches() {
        matchService.recoverExpiredMatches();
    }
}
