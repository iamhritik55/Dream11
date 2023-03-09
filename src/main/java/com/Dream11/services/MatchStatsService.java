package com.Dream11.services;

import com.Dream11.entity.*;
import com.Dream11.repo.MatchStatsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MatchStatsService {
    @Autowired
    MatchStatsRepo matchStatsRepo;
    @Autowired
    PlayerStatsService playerStatsService;
    @Autowired
    TeamService teamService;
    @Autowired
    MatchDetailsService matchDetailsService;
    public void createListPlayerStats(List<Player> playerList, String  matchId){

        if(matchStatsRepo.findById(matchId).isPresent()){

        }
        else {

        }
    }

    public void createMatchStats(String matchId){

        Match match = matchDetailsService.findMatchDetailsById(matchId);

        //Fetching team objects from db
        Team team1 = teamService.getTeamBYId(match.getTeam1Id());
        Team team2 = teamService.getTeamBYId(match.getTeam2Id());
        MatchStats matchStats = new MatchStats();
        matchStats.setId(matchId);

        matchStats.setTeam1Name(team1.getName());
        matchStats.setTeam2Name(team2.getName());

        //now I want to call a method that gives me a List<PlayerStats>
        matchStats.setTeam1PlayerStats(playerStatsService.convertPlayerIdListToPlayerStat(team1.getTeamPlayerIds()));
        matchStats.setTeam2PlayerStats(playerStatsService.convertPlayerIdListToPlayerStat(team2.getTeamPlayerIds()));
        matchStatsRepo.save(matchStats);
    }

    public void updateMatchStats(String matchId, List<Player> playerList, String teamId) throws Exception{
        MatchStats matchStats = findMatchStatsById(matchId);
        List<PlayerStats> playerStatsList=null;
        boolean team1=false;
        if(Objects.equals(matchStats.getTeam1Name(), teamService.getTeamBYId(teamId).getName())){
            playerStatsList = matchStats.getTeam1PlayerStats();
            team1 = true;
        }
        else if(Objects.equals(matchStats.getTeam2Name(), teamService.getTeamBYId(teamId).getName())){
            playerStatsList = matchStats.getTeam2PlayerStats();
        }


        //Now I want to update matchStats ->
        for(Player player: playerList){
            //A method where I sent playerStatsList, it returns me updatedPlayerStatsList
            playerStatsList = updatePlayerStatListForPlayer(playerStatsList, player);
        }

        if(team1){
            matchStats.setTeam1PlayerStats(playerStatsList);
        }
        else {
            matchStats.setTeam2PlayerStats(playerStatsList);
        }

        matchStatsRepo.save(matchStats);
    }

    public List<PlayerStats> updatePlayerStatListForPlayer(List<PlayerStats> playerStatsList, Player player){
        for(PlayerStats playerStats: playerStatsList){
            if(Objects.equals(playerStats.getPlayerId(), player.getId())){
                int foursScored = player.getFoursScored()+ playerStats.getFoursScored();
                int sixesScored = player.getSixesScored()+playerStats.getSixesScored();
                int battingRuns = player.getBattingRuns()+ playerStats.getBattingRuns();
                int bowlingWickets = player.getBowlingWickets()+ playerStats.getBowlingWickets();
                int playerPoints = player.getPlayerPoints()+ playerStats.getPlayerPoints();

                playerStats.setPlayerPoints(playerPoints);
                playerStats.setBattingRuns(battingRuns);
                playerStats.setBowlingWickets(bowlingWickets);
                playerStats.setSixesScored(sixesScored);
                playerStats.setFoursScored(foursScored);
            }
        }

        return playerStatsList;
    }

    public MatchStats findMatchStatsById(String id) throws Exception{
        if(matchStatsRepo.findById(id).isPresent()){
            return matchStatsRepo.findById(id).get();
        }
        else {
            throw new Exception("MatchStats id not found!");
        }
    }

    public void declareWinner(String id, String winnerTeamName) throws Exception{
        MatchStats matchStats = findMatchStatsById(id);
        matchStats.setWinnerTeamName(winnerTeamName);
        matchStatsRepo.save(matchStats);
    }
}
