package com.Dream11.services;

import com.Dream11.entity.Match;
import com.Dream11.entity.MatchPlayerStats;
import com.Dream11.entity.Player;
import com.Dream11.entity.Team;
import com.Dream11.repo.MatchRepo;
import com.Dream11.repo.PlayerRepo;
import com.Dream11.utility.CombinedMatchPlayerId;
import com.Dream11.utility.MatchPlayerStatsAttributes;
import com.Dream11.utility.MatchPlayerStatsResp;
import com.Dream11.utility.TeamDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    public MatchRepo matchRepo;
    @Autowired
    public TeamService teamService;
    @Autowired
    public PlayerService playerService;
    @Autowired
    public PlayerRepo playerRepo;
    @Autowired
    public MatchPlayerService matchPlayerService;
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

//    public MatchPlayerStatsResp getMatchStats(String matchId) throws Exception{
//                MatchPlayerStatsResp matchPlayerStatsResp = new MatchPlayerStatsResp();
//                Match match = getMatch(matchId);
//                Team team1 = teamService.getTeam(match.getTeam1Id());
//                Team team2 = teamService.getTeam(match.getTeam2Id());
//                matchPlayerStatsResp.setTeam1Name(team1.getName());
//                matchPlayerStatsResp.setTeam2Name(team2.getName());
//                List<String> team1PlayerIds = team1.getTeamPlayerIds();
//                List<String> team2PlayerIds = team2.getTeamPlayerIds();
//                List<MatchPlayerStatsAttributes> team1Stats = new ArrayList<>();
//                List<MatchPlayerStatsAttributes> team2Stats = new ArrayList<>();
//                for (String playerId : team1PlayerIds) {
//                    CombinedMatchPlayerId combinedMatchPlayerId = new CombinedMatchPlayerId(matchId, playerId);
//                    MatchPlayerStats matchPlayerStats = matchPlayerService.getMatchStats(combinedMatchPlayerId);
//                    MatchPlayerStatsAttributes matchPlayerStatsAttributes = new MatchPlayerStatsAttributes();
//                    matchPlayerStatsAttributes.setPlayerName(playerService.getPlayer(playerId).getName());
//                    matchPlayerStatsAttributes.setBattingRuns(matchPlayerStats.getBattingRuns());
//                    matchPlayerStatsAttributes.setBowlingWickets(matchPlayerStats.getBowlingRuns());
//                    matchPlayerStatsAttributes.setFoursScored(matchPlayerStats.getFoursScored());
//                    matchPlayerStatsAttributes.setSixesScored(matchPlayerStats.getSixesScored());
//                    matchPlayerStatsAttributes.setPlayerPoints(matchPlayerStats.getPlayerPoints());
//                    team1Stats.add(matchPlayerStatsAttributes);
//                }
//                matchPlayerStatsResp.setTeam1Stats(team1Stats);
//                for (String playerId : team2PlayerIds) {
//                    CombinedMatchPlayerId combinedMatchPlayerId = new CombinedMatchPlayerId(matchId, playerId);
//                    MatchPlayerStats matchPlayerStats = matchPlayerService.getMatchStats(combinedMatchPlayerId);
//                    MatchPlayerStatsAttributes matchPlayerStatsAttributes = new MatchPlayerStatsAttributes();
//                    matchPlayerStatsAttributes.setPlayerName(playerService.getPlayer(playerId).getName());
//                    matchPlayerStatsAttributes.setBattingRuns(matchPlayerStats.getBattingRuns());
//                    matchPlayerStatsAttributes.setBowlingWickets(matchPlayerStats.getBowlingRuns());
//                    matchPlayerStatsAttributes.setFoursScored(matchPlayerStats.getFoursScored());
//                    matchPlayerStatsAttributes.setSixesScored(matchPlayerStats.getSixesScored());
//                    matchPlayerStatsAttributes.setPlayerPoints(matchPlayerStats.getPlayerPoints());
//                    team2Stats.add(matchPlayerStatsAttributes);
//                }
//                matchPlayerStatsResp.setTeam2Stats(team2Stats);
//                return matchPlayerStatsResp;
//    }

}
