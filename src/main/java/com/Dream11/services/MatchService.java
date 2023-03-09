package com.Dream11.services;

import com.Dream11.entity.Match;
import com.Dream11.entity.Team;
import com.Dream11.repo.MatchRepo;
import com.Dream11.utility.MatchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class MatchService {
    @Autowired
    private MatchRepo matchRepo;
    @Autowired
    private TeamService teamService;
    @Autowired
    private MatchDetailsService matchDetailsService;
    @Autowired
    private InningService inningService;
    @Autowired
    private MatchUtils matchUtils;
    @Autowired
    private MatchStatsService matchStatsService;


    SecureRandom secureRandom = new SecureRandom();
    public void startMatch(String matchId) throws Exception{
        //Fetching match object from db
        Match match = matchDetailsService.findMatchDetailsById(matchId);

        //Create an object of matchStats and store it in DB
        matchStatsService.createMatchStats(matchId);

        //Fetching team objects from db
        Team team1 = teamService.getTeamBYId(match.getTeam1Id());
        Team team2 = teamService.getTeamBYId(match.getTeam2Id());

        //Toss 0-> team1 wins and bats, 1-> team2 wins and bats
        if(secureRandom.nextInt(2)==0){
            System.out.println(team1.getName()+" has won the toss and chosen to bat!");
            inningService.playInning(team1, team2, true, matchId);
            inningService.playInning(team2, team1, false, matchId);
        }
        else {
            System.out.println(team2.getName()+" has won the toss and chosen to bat!");
            inningService.playInning(team2, team1, true, matchId);
            inningService.playInning(team1, team2, false, matchId);
        }

        int team1score = matchDetailsService.getTeamScore(matchId, match.getTeam1Id());
        int team2score = matchDetailsService.getTeamScore(matchId, match.getTeam2Id());

        String winnerTeamName = matchUtils.declareWinner(team1, team2, team1score, team2score);
        matchStatsService.declareWinner(matchId,winnerTeamName);
        matchDetailsService.matchCompleted(matchId);

    }
}
