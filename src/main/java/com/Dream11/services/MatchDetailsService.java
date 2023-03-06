package com.Dream11.services;

import com.Dream11.entity.MatchDetails;
import com.Dream11.repo.MatchDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchDetailsService {
    @Autowired
    MatchDetailsRepo matchDetailsRepo;
    public void updateTeamScoreMatchDetails(int matchId, int teamId, int teamScore){
        MatchDetails matchDetails = matchDetailsRepo.findById(matchId).get();
        if(matchDetails.getTeam1Id()==teamId)
            matchDetails.setTeam1Score(teamScore);
        else
            matchDetails.setTeam2Score(teamScore);

        matchDetailsRepo.save(matchDetails);
    }

    public int getTeamScore(int matchId, int teamId){
        MatchDetails matchDetails = matchDetailsRepo.findById(matchId).get();

        if(matchDetails.getTeam1Id()==teamId)
            return matchDetails.getTeam1Score();
        else
            return matchDetails.getTeam2Score();

    }

}
