package com.Dream11.services;

import com.Dream11.entity.MatchPlayerStats;
import com.Dream11.repo.MatchPlayerStatsRepo;
import com.Dream11.utility.CombinedId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchPlayerService {
    @Autowired
    public MatchPlayerStatsRepo matchPlayerStatsRepo;

//    public List<MatchPlayerStats> getMatchStats(int matchId) {
//        return matchPlayerStatsRepo.findByMatchId(matchId);
//    }
    public MatchPlayerStats getMatchStats(CombinedId combinedId){

        return matchPlayerStatsRepo.findById(combinedId).get();
    }
}
