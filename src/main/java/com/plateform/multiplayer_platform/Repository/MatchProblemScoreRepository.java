package com.plateform.multiplayer_platform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plateform.multiplayer_platform.Entity.MatchProblemScore;
import com.plateform.multiplayer_platform.Entity.composite_key.MatchProblemScoreId;

public interface MatchProblemScoreRepository extends JpaRepository<MatchProblemScore,MatchProblemScoreId>{

}
