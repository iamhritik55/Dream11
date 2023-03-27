package com.Dream11.services;

import com.Dream11.entity.Match;
import com.Dream11.helperClasses.MatchStatus;
import com.Dream11.repo.MatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;



@Service
public class MatchDetailsService {
    @Autowired
    MatchRepo matchRepo;

    public Match findMatchDetailsById(String matchId){
        Match match;
        if(matchRepo.findById(matchId).isPresent()){
            match = matchRepo.findById(matchId).get();
            return match;
        }
        else{
            System.out.println("matchId not found!");
            return null;
        }

    }

    public void matchCompleted(String matchId){
        Match match = matchRepo.findById(matchId).get();
        match.setStatus(MatchStatus.PLAYED);
        matchRepo.save(match);
    }

}
