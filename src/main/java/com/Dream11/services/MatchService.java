package com.Dream11.services;

import com.Dream11.entity.Match;
import com.Dream11.entity.MatchStats;
import com.Dream11.entity.MatchUserStats;
import com.Dream11.entity.Team;
import com.Dream11.repo.MatchRepo;
import com.Dream11.utility.MatchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

import static com.Dream11.Counter.counter;

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
    @Autowired
            private MatchUserService matchUserService;

    SecureRandom secureRandom = new SecureRandom();
    public List<MatchUserStats> startMatch(String matchId) throws Exception{
        counter=0;
        //Fetching match object from db
        Match match = matchDetailsService.findMatchDetailsById(matchId);
        if(match.isCompleted()){
            throw new Exception("Match has already happened!");
        }
        //Create an object of matchStats and store it in DB
        matchStatsService.createMatchStats(matchId);

        //Fetching team objects from db
        Team team1 = teamService.getTeamBYId(match.getTeam1Id());
        Team team2 = teamService.getTeamBYId(match.getTeam2Id());
        MatchStats matchStats;
        List<MatchUserStats> matchUserStatsList;
        //Toss 0-> team1 wins and bats, 1-> team2 wins and bats
        if(secureRandom.nextInt(2)==0){
            System.out.println(team1.getName()+" has won the toss and chosen to bat!");
            matchUserStatsList = inningService.playInning(team1, team2, true, matchId);
            matchUserStatsList = inningService.playInning(team2, team1, false, matchId);
        }
        else {
            System.out.println(team2.getName()+" has won the toss and chosen to bat!");
            matchUserStatsList=inningService.playInning(team2, team1, true, matchId);
            matchUserStatsList=inningService.playInning(team1, team2, false, matchId);
        }

        int team1score = matchDetailsService.getTeamScore(matchId, match.getTeam1Id());
        int team2score = matchDetailsService.getTeamScore(matchId, match.getTeam2Id());

        String winnerTeamName = matchUtils.declareWinner(team1, team2, team1score, team2score);
        matchStats = matchStatsService.declareWinner(matchId,winnerTeamName);
        matchDetailsService.matchCompleted(matchId);
        //Updating points for user
        matchUserService.updateWinnerUserPoints(matchId);
        return matchUserStatsList;
    }
}
