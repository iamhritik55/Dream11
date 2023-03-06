package com.Dream11.services;

import com.Dream11.entity.MatchDetails;
import com.Dream11.repo.MatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    @Autowired
    public MatchRepo matchRepo;

    public MatchDetails addMatch(MatchDetails matchDetails) {

        return matchRepo.save(matchDetails);
    }

    public List<MatchDetails> getMatches() {
        return matchRepo.findAll();
    }

    public MatchDetails getMatch(int matchId) {

        return matchRepo.findById(matchId).get();
    }
}
