package com.Dream11.controller;

import com.Dream11.entity.MatchDetails;
import com.Dream11.entity.MatchPlayerStats;
import com.Dream11.entity.Player;
import com.Dream11.entity.Team;
import com.Dream11.services.MatchPlayerService;
import com.Dream11.services.MatchService;
import com.Dream11.services.PlayerService;
import com.Dream11.services.TeamService;
import com.Dream11.utility.CombinedMatchPlayerId;
import com.Dream11.utility.DispTeamDetResp;
import com.Dream11.utility.MatchPlayerStatsAttributes;
import com.Dream11.utility.MatchPlayerStatsResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchAPI {

    @Autowired
    public MatchService matchService;
    @Autowired
    public TeamService teamService; //doubt- can I use teamService in matchAPI
    @Autowired
    public PlayerService playerService;//same doubt as above
    @Autowired
    public MatchPlayerService matchPlayerService;


    @PostMapping
    // TODO: 06/03/23  take match DTO which will update the necessary fields
    // TODO: 06/03/23  Give in response whatever fields is required.
    public MatchDetails addMatch(@RequestBody MatchDetails matchDetails) {
        return matchService.addMatch(matchDetails);
    }

    // TODO: 06/03/23 Add a new API to get the live matches.
    @GetMapping
    public List<MatchDetails> getMatches() {
        return matchService.getMatches();
    }

    @GetMapping("/{matchId}")
    // TODO: 06/03/23 rename this var  DispTeamDetResp
    // TODO: 06/03/23 Take string as input
    public DispTeamDetResp getMatchDetails(@PathVariable int matchId) {
        MatchDetails matchDetails;
        try {
            matchDetails = matchService.getMatch(matchId);
        } catch (Exception e) {
            System.out.println("match with matchId - " + matchId + " doesn't exist");
            // TODO: 06/03/23 throw exception.
            return null;
        }
        DispTeamDetResp dispTeamDetResp = new DispTeamDetResp();
        dispTeamDetResp.setTeam1Id(matchDetails.getTeam1Id());
        dispTeamDetResp.setTeam2Id(matchDetails.getTeam2Id());
        Team team1 = teamService.getTeam(matchDetails.getTeam1Id());
        Team team2 = teamService.getTeam(matchDetails.getTeam2Id());
        dispTeamDetResp.setTeam1Name(team1.getName());
        dispTeamDetResp.setTeam2Name(team2.getName());
        List<Integer> team1PlayerIds = team1.getTeamPlayerIds();
        List<Integer> team2PlayerIds = team2.getTeamPlayerIds();
        List<Player> team1Players = new ArrayList<>();
        List<Player> team2Players = new ArrayList<>();
        if (!CollectionUtils.isEmpty(team1PlayerIds)) {
            for (Integer playerId : team1PlayerIds) {
                team1Players.add(playerService.getPlayer(playerId));
            }
        }

        for (Integer playerId : team2PlayerIds) {
            team2Players.add(playerService.getPlayer(playerId));
        }
        dispTeamDetResp.setTeam1Players(team1Players);
        dispTeamDetResp.setTeam2Players(team2Players);
        return dispTeamDetResp;
    }

    @GetMapping("/matchStats/{matchId}")
    public MatchPlayerStatsResp getMatchStats(@PathVariable int matchId) {
        MatchPlayerStatsResp matchPlayerStatsResp = new MatchPlayerStatsResp();
        MatchDetails matchDetails = matchService.getMatch(matchId);
        Team team1 = teamService.getTeam(matchDetails.getTeam1Id());
        Team team2 = teamService.getTeam(matchDetails.getTeam2Id());
        matchPlayerStatsResp.setTeam1Name(team1.getName());
        matchPlayerStatsResp.setTeam2Name(team2.getName());
        List<Integer> team1PlayerIds = team1.getTeamPlayerIds();
        List<Integer> team2PlayerIds = team2.getTeamPlayerIds();
        List<MatchPlayerStatsAttributes> team1Stats = new ArrayList<>();
        List<MatchPlayerStatsAttributes> team2Stats = new ArrayList<>();
        for (Integer playerId : team1PlayerIds) {
            CombinedMatchPlayerId combinedMatchPlayerId = new CombinedMatchPlayerId(matchId, playerId);
            MatchPlayerStats matchPlayerStats = matchPlayerService.getMatchStats(combinedMatchPlayerId);
            MatchPlayerStatsAttributes matchPlayerStatsAttributes = new MatchPlayerStatsAttributes();
            matchPlayerStatsAttributes.setPlayerName(playerService.getPlayer(playerId).getName());
            matchPlayerStatsAttributes.setBattingRuns(matchPlayerStats.getBattingRuns());
            matchPlayerStatsAttributes.setBowlingWickets(matchPlayerStats.getBowlingRuns());
            matchPlayerStatsAttributes.setFoursScored(matchPlayerStats.getFoursScored());
            matchPlayerStatsAttributes.setSixesScored(matchPlayerStats.getSixesScored());
            matchPlayerStatsAttributes.setPlayerPoints(matchPlayerStats.getPlayerPoints());
            team1Stats.add(matchPlayerStatsAttributes);
        }
        matchPlayerStatsResp.setTeam1Stats(team1Stats);
        for (Integer playerId : team2PlayerIds) {
            CombinedMatchPlayerId combinedMatchPlayerId = new CombinedMatchPlayerId(matchId, playerId);
            MatchPlayerStats matchPlayerStats = matchPlayerService.getMatchStats(combinedMatchPlayerId);
            MatchPlayerStatsAttributes matchPlayerStatsAttributes = new MatchPlayerStatsAttributes();
            matchPlayerStatsAttributes.setPlayerName(playerService.getPlayer(playerId).getName());
            matchPlayerStatsAttributes.setBattingRuns(matchPlayerStats.getBattingRuns());
            matchPlayerStatsAttributes.setBowlingWickets(matchPlayerStats.getBowlingRuns());
            matchPlayerStatsAttributes.setFoursScored(matchPlayerStats.getFoursScored());
            matchPlayerStatsAttributes.setSixesScored(matchPlayerStats.getSixesScored());
            matchPlayerStatsAttributes.setPlayerPoints(matchPlayerStats.getPlayerPoints());
            team2Stats.add(matchPlayerStatsAttributes);
        }
        matchPlayerStatsResp.setTeam2Stats(team2Stats);
        return matchPlayerStatsResp;
    }
}
