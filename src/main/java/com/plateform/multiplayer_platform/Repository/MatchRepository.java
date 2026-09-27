package com.plateform.multiplayer_platform.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plateform.multiplayer_platform.Entity.Match;

public interface MatchRepository extends JpaRepository<Match,Long>{

}
