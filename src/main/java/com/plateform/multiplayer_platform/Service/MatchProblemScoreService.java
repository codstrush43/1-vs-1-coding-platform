package com.plateform.multiplayer_platform.Service;

import com.plateform.multiplayer_platform.Entity.MatchProblemScore;

public interface MatchProblemScoreService {

    MatchProblemScore createMatchProblemScore(long user_id,long match_id,long problem_id);
    void updateMatchProblemScore(long user_id,long match_id,long problem_id);
    MatchProblemScore findById(long user_id, long match_id, long problem_id);
    boolean existsByUser_IdAndMatch_IdAndProblem_Id(long user_id, long match_id, long problem_id);

}
