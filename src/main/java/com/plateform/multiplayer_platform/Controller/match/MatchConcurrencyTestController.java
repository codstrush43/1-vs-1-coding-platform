package com.plateform.multiplayer_platform.Controller.match;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.plateform.multiplayer_platform.Entity.Match;
import com.plateform.multiplayer_platform.Service.MatchCompletionService;

@RestController
@RequestMapping("/api/test/matches")
public class MatchConcurrencyTestController {

    @Autowired
    private MatchCompletionService matchCompletionService;

    @PostMapping("/{matchId}/complete")
    public ResponseEntity<?> completeMatch(@PathVariable long matchId) {

        try {
            Match match = matchCompletionService.stopMatch(matchId);

            return ResponseEntity.ok(
                Map.of(
                    "matchId", match.getId(),
                    "status", match.getStatus().name()
                )
            );

        } catch (OptimisticLockingFailureException e) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                    "message", "Another request completed this match."
                )
            );
        }
    }
}

