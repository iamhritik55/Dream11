package com.Dream11.gamecontroller;

import com.Dream11.entity.MatchStats;
import com.Dream11.entity.MatchUserStats;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameControllerService{
    public List<MatchUserStats> startMatch(String matchId) throws Exception;
}
