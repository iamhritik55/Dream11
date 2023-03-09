package com.Dream11.services;

import com.Dream11.entity.MatchUserStats;
import com.Dream11.entity.Player;
import com.Dream11.repo.MatchUserStatsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchUserService {

    @Autowired
    MatchUserStatsRepo matchUserStatsRepo;

    //So MatchUserStats already exist, I want to fetch it from db and update it
    public void updateMultipleMatchUserStats(String matchId, List<Player> combinedPlayerList) throws Exception{
        List<MatchUserStats> matchUserStats = findByMatchId(matchId);

    }

    public void updateSingleMatchUserStats(MatchUserStats matchUserStats, List<Player> playerList){
        List<String> chosenList = matchUserStats.getChosenPlayerIdList();

        for(String playerId: chosenList){

        }
    }

    public List<MatchUserStats> findByMatchId(String matchId) throws Exception{
        List<MatchUserStats> matchUserStats= matchUserStatsRepo.findByMatchId(matchId);
        if(matchUserStats!=null)
            return matchUserStats;
        else
            throw new Exception("No record found with corresponding matchId");

    }
}
