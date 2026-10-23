package com.plateform.multiplayer_platform.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.plateform.multiplayer_platform.Entity.Match;
import com.plateform.multiplayer_platform.Entity.MatchQueue;
import com.plateform.multiplayer_platform.Entity.User;
import com.plateform.multiplayer_platform.Enum.MatchStatus;
import com.plateform.multiplayer_platform.Repository.MatchQueueRepository;
import com.plateform.multiplayer_platform.Repository.MatchRepository;
import com.plateform.multiplayer_platform.Repository.UserRepository;
import com.plateform.multiplayer_platform.Service.MatchQueueService;

public class MatchQueueServiceImpl implements MatchQueueService{

    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private MatchQueueRepository matchQueueRepository;

    @Autowired 
    private MatchRepository matchRepository;

    @Override 
    public MatchQueue joinMatch(long userId)
    {
        User user = userRepository.findByUserId(userId).
                                    orElseThrow(()->{throw new RuntimeException("User not found in matchmaking queue"); });
        if(matchQueueRepository.findByUserId(userId).orElse(null) != null)
        {
            throw new RuntimeException("player already in match queue");
        }

        MatchQueue matchQueue = new MatchQueue();
        matchQueue.setUser(user);

        MatchQueue savedQueue = matchQueueRepository.save(matchQueue);

        findMatch();

        return savedQueue;
    }

    @Override 
    public void findMatch() {
        List<MatchQueue> players = matchQueueRepository.findFirstTwoPlayers();

        if (players.size() < 2) {
                return;
        }
        MatchQueue firstPlayer = players.get(0);
        MatchQueue secondPlayer = players.get(1);

        User player1 = firstPlayer.getUser();
        User player2 = secondPlayer.getUser();
        Match match = new Match();

        match.setPlayer1(player1);
        match.setPlayer2(player2);
        match.setStatus(MatchStatus.READY);

        matchRepository.save(match);
    }

}
