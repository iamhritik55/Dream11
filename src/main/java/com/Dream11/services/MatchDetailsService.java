package com.Dream11.services;

import com.Dream11.entity.Match;
import com.Dream11.enums.MatchStatus;
import com.Dream11.repo.MatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MatchDetailsService {
    @Autowired
    MatchRepo matchRepo;

    public void matchCompleted(String matchId){
        Match match = matchRepo.findById(matchId).get();
        match.setStatus(MatchStatus.PLAYED);
        matchRepo.save(match);
    }

}
