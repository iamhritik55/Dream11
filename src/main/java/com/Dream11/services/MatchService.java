package com.Dream11.services;

import com.Dream11.entity.*;

import com.Dream11.entity.MatchDetails;
import com.Dream11.repo.MatchDetailsRepo;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.repo.MatchUserStatsRepo;
import com.Dream11.repo.MatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    private InningService inningService;
    @Autowired
    public MatchRepo matchRepo;
    @Autowired
    private MatchDetailsRepo matchDetailsRepo;
    @Autowired
    private MatchUserStatsRepo matchUserStatsRepo;
    @Autowired
    private TeamService teamService;
    @Autowired
    private MatchDetailsService matchDetailsService;
    @Autowired
    private PlayerRepo playerRepo;
    @Autowired
    private PlayerService playerService;

    SecureRandom secureRandom = new SecureRandom();

    public MatchDetails addMatch(MatchDetails matchDetails) {
        return matchRepo.save(matchDetails);
    }

    public List<MatchDetails> getMatches() {
        return matchRepo.findAll();
    }

    public MatchDetails getMatch(int matchId) {
        return matchRepo.findById(matchId).get();
    }

    public void startMatch(int matchId) {
        //So I am fetch team1Id and team2Id from matchDetails
        MatchDetails matchDetails;
        if (matchDetailsRepo.findById(matchId).isPresent()) {
            matchDetails = matchDetailsRepo.findById(matchId).get();
        } else {
            System.out.println("MatchID not found!");
            return;
        }


        Team team1 = teamService.getTeamBYId(matchDetails.getTeam1Id());
        Team team2 = teamService.getTeamBYId(matchDetails.getTeam2Id());

        //Toss 0-> team1 wins and bats, 1-> team2 wins and bats
        if (secureRandom.nextInt(2) == 0) {
            System.out.println(team1.getName() + " has won the toss and chosen to bat!");
            inningService.playInning(team1, team2, true, matchId);
            inningService.playInning(team2, team1, false, matchId);
        } else {
            System.out.println(team2.getName() + " has won the toss and chosen to bat!");
            inningService.playInning(team2, team1, true, matchId);
            inningService.playInning(team1, team2, false, matchId);
        }

        int team1score = matchDetailsService.getTeamScore(matchId, matchDetails.getTeam1Id());
        int team2score = matchDetailsService.getTeamScore(matchId, matchDetails.getTeam2Id());

        if (team1score > team2score) {
            System.out.println(team1.getName() + " has won the match");
        } else if (team2score > team1score) {
            System.out.println(team2.getName() + " has won the match");
        } else {
            System.out.println("The match is tied!");

        }
    }

}
