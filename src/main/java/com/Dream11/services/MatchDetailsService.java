package com.Dream11.services;

import com.Dream11.entity.Match;
import com.Dream11.entity.MatchStatus;
import com.Dream11.repo.MatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.Dream11.Counter.counter;

@Service
public class MatchDetailsService {
    @Autowired
    MatchRepo matchRepo;
    public void updateTeamScoreMatchDetails(String matchId, String teamId, int teamScore){
        Match match = matchRepo.findById(matchId).get();
        counter++;
        if(Objects.equals(match.getTeam1Id(), teamId))
            match.setTeam1Score(teamScore);
        else
            match.setTeam2Score(teamScore);

        matchRepo.save(match);
        counter++;
    }

    public Match findMatchDetailsById(String matchId){
        Match match;
        if(matchRepo.findById(matchId).isPresent()){
            counter++;
            counter++;
            match = matchRepo.findById(matchId).get();
            return match;
        }
        else{
            System.out.println("matchId not found!");
            return null;
        }

    }

    public long getTeamScore(String matchId, String teamId){
        Match match = matchRepo.findById(matchId).get();
        counter++;
        if(match.getTeam1Id()==teamId)
            return match.getTeam1Score();
        else
            return match.getTeam2Score();

    }

    public void matchCompleted(String matchId){
        Match match = matchRepo.findById(matchId).get();
        match.setCompleted(MatchStatus.PLAYED);
        matchRepo.save(match);
        counter++;
        counter++;
    }

}
