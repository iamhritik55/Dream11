package com.Dream11.controller;

import com.Dream11.entity.MatchPlayerStats;
import com.Dream11.entity.MatchUserStats;
import com.Dream11.services.MatchPlayerService;
import com.Dream11.services.MatchService;
import com.Dream11.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Dream11.entity.MatchDetails;
import com.Dream11.entity.Player;
import com.Dream11.entity.Team;
import com.Dream11.services.PlayerService;
import com.Dream11.services.TeamService;
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

    @PostMapping("/start/{matchId}")
    public void startMatch(@PathVariable(value = "matchId") int matchId) {
        matchService.startMatch(matchId);
    }

    @DeleteMapping
    public void deleteAllMatchPlayerStats() {
        matchPlayerService.deleteAll();
    }


    @PostMapping
    public MatchDetails addMatch(@RequestBody MatchDetails matchDetails) {
        return matchService.addMatch(matchDetails);
    }

    @GetMapping
    public List<MatchDetails> getMatches() {
        return matchService.getMatches();
    }
    @PostMapping("/")
    public MatchUserStats addMatchUserStats(@RequestBody MatchUserStats matchUserStats) {
        return matchService.addMatchUserStats(matchUserStats);
    }
    @GetMapping("/{matchId}/{userId}")
    public ResponseEntity<Object> displayMatchUserStats(@PathVariable int matchId, @PathVariable int userId){
        try{
            CombinedMatchUserId combinedMatchUserId = new CombinedMatchUserId(matchId, userId);
            MatchUserStats matchUserStats = matchService.getUserStats(combinedMatchUserId);
            return new ResponseEntity<>(matchUserStats, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/")
    public List<MatchUserStats> getMatchStats(){
        return matchService.getAllStats();
    }

    @GetMapping("/{matchId}")
    public DispTeamDetResp displayTeamDetails(@PathVariable int matchId) {
        MatchDetails matchDetails;
        try {
            matchDetails = matchService.getMatch(matchId);
        } catch (Exception e) {
            System.out.println("match with matchId - " + matchId + " doesn't exist");
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

        for (Integer playerId : team1PlayerIds) {
            team1Players.add(playerService.getPlayer(playerId));
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
            CombinedMatchPlayerId combinedId = new CombinedMatchPlayerId(matchId, playerId);
            MatchPlayerStats matchPlayerStats = matchPlayerService.getMatchStats(combinedId);
            MatchPlayerStatsAttributes matchPlayerStatsAttributes = new MatchPlayerStatsAttributes();
            matchPlayerStatsAttributes.setPlayerName(playerService.getPlayer(playerId).getName());
            matchPlayerStatsAttributes.setBattingRuns(matchPlayerStats.getBattingRuns());
            matchPlayerStatsAttributes.setBowlingWickets(matchPlayerStats.getBowlingWickets());
            matchPlayerStatsAttributes.setFoursScored(matchPlayerStats.getFoursScored());
            matchPlayerStatsAttributes.setSixesScored(matchPlayerStats.getSixesScored());
            matchPlayerStatsAttributes.setPlayerPoints(matchPlayerStats.getPlayerPoints());
            team1Stats.add(matchPlayerStatsAttributes);
        }
        matchPlayerStatsResp.setTeam1Stats(team1Stats);
        for (Integer playerId : team2PlayerIds) {
            CombinedMatchPlayerId combinedId = new CombinedMatchPlayerId(matchId, playerId);
            MatchPlayerStats matchPlayerStats = matchPlayerService.getMatchStats(combinedId);
            MatchPlayerStatsAttributes matchPlayerStatsAttributes = new MatchPlayerStatsAttributes();
            matchPlayerStatsAttributes.setPlayerName(playerService.getPlayer(playerId).getName());
            matchPlayerStatsAttributes.setBattingRuns(matchPlayerStats.getBattingRuns());
            matchPlayerStatsAttributes.setBowlingWickets(matchPlayerStats.getBowlingWickets());
            matchPlayerStatsAttributes.setFoursScored(matchPlayerStats.getFoursScored());
            matchPlayerStatsAttributes.setSixesScored(matchPlayerStats.getSixesScored());
            matchPlayerStatsAttributes.setPlayerPoints(matchPlayerStats.getPlayerPoints());
            team2Stats.add(matchPlayerStatsAttributes);
        }
        matchPlayerStatsResp.setTeam2Stats(team2Stats);
        return matchPlayerStatsResp;
    }
}
