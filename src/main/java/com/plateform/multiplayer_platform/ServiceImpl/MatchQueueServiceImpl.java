package com.plateform.multiplayer_platform.ServiceImpl;

import com.plateform.multiplayer_platform.ServiceImpl.MatchProblemScoreServiceImpl;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.Entity.Match;
import com.plateform.multiplayer_platform.Entity.MatchProblemScore;
import com.plateform.multiplayer_platform.Entity.MatchQueue;
import com.plateform.multiplayer_platform.Entity.PlayerScore;
import com.plateform.multiplayer_platform.Entity.Problem;
import com.plateform.multiplayer_platform.Entity.User;
import com.plateform.multiplayer_platform.Enum.MatchStatus;
import com.plateform.multiplayer_platform.Repository.MatchQueueRepository;
import com.plateform.multiplayer_platform.Repository.MatchRepository;
import com.plateform.multiplayer_platform.Repository.UserRepository;
import com.plateform.multiplayer_platform.Service.MatchProblemScoreService;
import com.plateform.multiplayer_platform.Service.MatchQueueService;
import com.plateform.multiplayer_platform.Service.MatchService;
import com.plateform.multiplayer_platform.Service.PlayerScoreService;
import com.plateform.multiplayer_platform.Service.ProblemService;

import org.springframework.transaction.annotation.Transactional;

@Service 
public class MatchQueueServiceImpl implements MatchQueueService{

    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private MatchQueueRepository matchQueueRepository;

    @Autowired 
    private MatchRepository matchRepository;

    @Autowired
    @Qualifier("getScheduler") 
    private TaskScheduler matchScheduler;

    @Autowired 
    private MatchService matchService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired 
    private PlayerScoreService playerScoreService;

    @Autowired 
    private MatchProblemScoreService matchProblemScoreService;

    @Autowired 
    private ProblemService problemService;

    @Transactional 
    @Override 
    public MatchQueue joinMatch(long userId)
    {
        try{
        
        matchQueueRepository.lockMatchmaking(); //2 request parallel aavse to 2nd ubho rehse and 1 continue karse..

        User user = userRepository.findById(userId).
                                    orElseThrow(()->new RuntimeException("User not found in matchmaking queue"));
        
        if(matchQueueRepository.findByUserId(userId).orElse(null) != null)
        {
            throw new RuntimeException("player already in match queue");
        }

        boolean alreadyInMatch =
            matchRepository.countActiveMatches(
                userId,
                List.of(MatchStatus.READY, MatchStatus.RUNNING)
            ) > 0;

        if(alreadyInMatch)
        {
            throw new RuntimeException("User is already in the match!");
        }

        MatchQueue matchQueue = new MatchQueue();
        matchQueue.setUser(user);

        MatchQueue savedQueue = matchQueueRepository.save(matchQueue);

        findMatch();

        return savedQueue;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override 
    @Transactional 
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

        
        matchQueueRepository.delete(firstPlayer);
        matchQueueRepository.delete(secondPlayer);
        
        matchRepository.save(match);

        PlayerScore playerScore1=new PlayerScore();
        playerScore1.setMatch(match);
        playerScore1.setUser(player1);
        playerScore1.setScore(0);

        PlayerScore playerScore2=new PlayerScore();
        playerScore2.setMatch(match);
        playerScore2.setUser(player2);
        playerScore2.setScore(0);

        playerScoreService.savePlayerScore(playerScore1);
        playerScoreService.savePlayerScore(playerScore2);

        Problem problem=problemService.findById(1L);
        
        matchProblemScoreService.createMatchProblemScore(player1.getId(),match.getId(),problem.getId());
        matchProblemScoreService.createMatchProblemScore(player2.getId(),match.getId(),problem.getId());
        
        messagingTemplate.convertAndSend("/topic/match/" + match.getId(), "MATCH_READY");

        matchScheduler.schedule(()->{matchService.startMatch(match.getId());}, Instant.now().plusSeconds(5));
    }
}