package com.Dream11.services;

import com.Dream11.entity.*;
import com.Dream11.repo.MatchRepo;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.utility.CombinedMatchPlayerId;
import com.Dream11.utility.MatchUtils;
import com.Dream11.utility.TeamDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
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
    @Autowired
    public PlayerService playerService;
    @Autowired
    public PlayerRepo playerRepo;
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
        return matchUserService.findByMatchId(matchId);
    }
    public Match addMatch(Match match) {
        return matchRepo.save(match);
    }

    public List<Match> getMatches() {
        return matchRepo.findAll();
    }

    public Match getMatch(String matchId) throws Exception {
        if (matchRepo.findById(matchId).isPresent()) {
            return matchRepo.findById(matchId).get();
        } else {
            throw new Exception("Match with matchId - " + matchId + " doesn't exist");
        }
    }
    public List<Match> getUnplayedMatches(){
        return matchRepo.findMatchesByStatus(false);
    }
    public List<Match> getPlayedMatches() {
        return matchRepo.findMatchesByStatus(true);
    }
    public TeamDetails getTeamDetails(String matchId) throws Exception {
        Match match;
        match = getMatch(matchId);
        TeamDetails teamDetails = new TeamDetails();
        teamDetails.setTeam1Id(match.getTeam1Id());
        teamDetails.setTeam2Id(match.getTeam2Id());
        Team team1 = teamService.getTeam(match.getTeam1Id());
        Team team2 = teamService.getTeam(match.getTeam2Id());
        teamDetails.setTeam1Name(team1.getName());
        teamDetails.setTeam2Name(team2.getName());
        List<String> team1PlayerIds = team1.getTeamPlayerIds();
        List<String> team2PlayerIds = team2.getTeamPlayerIds();
        List<Player> team1Players = new ArrayList<>();
        List<Player> team2Players = new ArrayList<>();
        team1Players=playerRepo.findAllById(team1PlayerIds);
        team2Players=playerRepo.findAllById(team2PlayerIds);
        //        if (!CollectionUtils.isEmpty(team1PlayerIds)) {
        //            for (String playerId : team1PlayerIds) {
        //                team1Players.add(playerService.getPlayer(playerId));
        //            }
        //        }
        //
        //        for (String playerId : team2PlayerIds) {
        //            team2Players.add(playerService.getPlayer(playerId));
        //        }
        teamDetails.setTeam1Players(team1Players);
        teamDetails.setTeam2Players(team2Players);
        return teamDetails;
    }

}
