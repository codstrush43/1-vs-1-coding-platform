package com.plateform.multiplayer_platform.ServiceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.ProblemReporter;
import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.Entity.MatchProblemScore;
import com.plateform.multiplayer_platform.Entity.composite_key.MatchProblemScoreId;
import com.plateform.multiplayer_platform.Repository.MatchProblemScoreRepository;
import com.plateform.multiplayer_platform.Repository.MatchRepository;
import com.plateform.multiplayer_platform.Repository.ProblemRepository;
import com.plateform.multiplayer_platform.Repository.UserRepository;
import com.plateform.multiplayer_platform.Service.MatchProblemScoreService;
import com.plateform.multiplayer_platform.Service.MatchService;
import com.plateform.multiplayer_platform.Service.ProblemService;
import com.plateform.multiplayer_platform.Service.UserService;

import org.springframework.transaction.annotation.Transactional;

@Transactional 
@Service 
public class MatchProblemScoreServiceImpl implements MatchProblemScoreService{

    @Autowired  
    private MatchProblemScoreRepository matchProblemScoreRepository;

    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private MatchRepository matchRepository;

    @Autowired 
    private ProblemRepository problemRepository;

    @Override
    public boolean checkAlreadyUpdated(long user_id, long match_id, long problem_id) {

        MatchProblemScore matchProblemScore=findById(user_id,match_id,problem_id);

        if(matchProblemScore.getPointsAwarded()>0)
            return true;
        
        return false;
    }

    @Override
    public void createMatchProblemScore(long user_id, long match_id, long problem_id) {

        MatchProblemScore matchProblemScore = new MatchProblemScore();

        matchProblemScore.setUser(userRepository.findById(user_id).orElseThrow(()->{throw new RuntimeException("user not found in matchproblemScore!");}));
        matchProblemScore.setMatch(matchRepository.findById(match_id).orElseThrow(()->{throw new RuntimeException("match not found in matchproblemScore!");}));
        matchProblemScore.setProblem(problemRepository.findById(problem_id).orElseThrow(()->{throw new RuntimeException("problem not found in matchproblemScore!");}));
        matchProblemScore.setPointsAwarded(0);
        
        matchProblemScoreRepository.save(matchProblemScore);
        
    }

    @Override
    public void updateMatchProblemScore(long user_id, long match_id, long problem_id) {
        MatchProblemScore matchProblemScore=findById(user_id,match_id,problem_id);

        matchProblemScore.setPointsAwarded(100+matchProblemScore.getPointsAwarded());

        matchProblemScoreRepository.save(matchProblemScore);
    }

    @Override 
    public MatchProblemScore findById(long user_id, long match_id, long problem_id)
    {
        MatchProblemScoreId matchProblemScoreId = new MatchProblemScoreId(user_id,match_id,problem_id);

        return matchProblemScoreRepository.findById(matchProblemScoreId)
                                        .orElseThrow(()->{throw new RuntimeException("match problem score not found while updating it!");});
    }

}
