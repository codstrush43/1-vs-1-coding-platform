package com.plateform.multiplayer_platform.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.plateform.multiplayer_platform.Entity.Match;
import com.plateform.multiplayer_platform.Repository.MatchRepository;
import com.plateform.multiplayer_platform.Service.MatchService;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired 
    private MatchRepository matchRepository;

    @Override 
    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

}
