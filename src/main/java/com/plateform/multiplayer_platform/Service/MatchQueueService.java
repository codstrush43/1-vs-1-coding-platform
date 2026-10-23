package com.plateform.multiplayer_platform.Service;

import org.springframework.stereotype.Service;

import com.plateform.multiplayer_platform.Entity.MatchQueue;

@Service 
public interface MatchQueueService {

    MatchQueue joinMatch(long userId);

    void findMatch(); 

}
