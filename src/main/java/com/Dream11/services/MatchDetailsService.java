package com.Dream11.services;

import com.Dream11.entity.Match;
import com.Dream11.repo.MatchDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchDetailsService {
    @Autowired
    MatchDetailsRepo matchDetailsRepo;
    public void updateTeamScoreMatchDetails(String matchId, String teamId, int teamScore){
        Match match = matchDetailsRepo.findById(matchId).get();
        if(match.getTeam1Id()==teamId)
            match.setTeam1Score(teamScore);
        else
            match.setTeam2Score(teamScore);

        matchDetailsRepo.save(match);
    }

    public Match findMatchDetailsById(String matchId){
        Match match;
        if(matchDetailsRepo.findById(matchId).isPresent()){
            match = matchDetailsRepo.findById(matchId).get();
            return match;
        }
        else{
            System.out.println("matchId not found!");
            return null;
        }

    }

    public int getTeamScore(String matchId, String teamId){
        Match match = matchDetailsRepo.findById(matchId).get();

        if(match.getTeam1Id()==teamId)
            return match.getTeam1Score();
        else
            return match.getTeam2Score();

    }

}
