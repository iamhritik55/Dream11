package com.Dream11.services;

import com.Dream11.services.context.CricketInningContext;
import com.Dream11.services.context.CricketMatchContext;
import com.Dream11.services.models.MatchStats;
import com.Dream11.services.models.Player;
import com.Dream11.services.models.PlayerStats;
import com.Dream11.services.repo.MatchStatsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class MatchStatsService {

    @Autowired
    MatchStatsRepo matchStatsRepo;

    @Autowired
    TeamService teamService;

    private List<PlayerStats> createPlayerStatsList(List<Player> playerList){
        List<PlayerStats> playerStatsList = new ArrayList<>();
        for(Player player: playerList){
            playerStatsList.add(createPlayerStatsFromPlayer(player));
        }
        return playerStatsList;
    }

    private PlayerStats createPlayerStatsFromPlayer(Player player){
        PlayerStats playerStats= new PlayerStats();
        playerStats.setPlayerId(player.getId());
        playerStats.setPlayerName(player.getName());
        playerStats.setPlayerPoints(player.getPlayerPoints());
        playerStats.setFoursScored(player.getFoursScored());
        playerStats.setBowlingWickets(player.getBowlingWickets());
        playerStats.setSixesScored(player.getSixesScored());
        playerStats.setBattingRuns(player.getBattingRuns());
        return playerStats;
    }
    private String getWinnerTeamName(CricketMatchContext matchContext){
        int team1Runs = matchContext.getTeam1().getTeamRuns();
        int team2Runs = matchContext.getTeam2().getTeamRuns();

        if(team1Runs>team2Runs){
            return matchContext.getTeam1().getName();
        }
        else if(team1Runs<team2Runs){
            return matchContext.getTeam2().getName();
        }
        else {
            return "Tied";
        }

    }
    public MatchStats storeAllMatchData(CricketMatchContext matchContext, CricketInningContext inningContext){
        MatchStats matchStats = new MatchStats();

        matchStats.setId(matchContext.getMatch().getMatchId());

        matchStats.setTeam1Name(matchContext.getTeam1().getName());
        matchStats.setTeam2Name(matchContext.getTeam2().getName());

        matchStats.setTeam1Score(matchContext.getTeam1().getTeamRuns());
        matchStats.setTeam2Score(matchContext.getTeam2().getTeamRuns());

        if(Objects.equals(matchContext.getTeam1().getId(), inningContext.getBattingTeamId())){
            matchStats.setTeam1PlayerStats(createPlayerStatsList(inningContext.getBattingPlayerList()));
            matchStats.setTeam2PlayerStats(createPlayerStatsList(inningContext.getBowlingPlayerList()));
        }
        else {
            matchStats.setTeam1PlayerStats(createPlayerStatsList(inningContext.getBowlingPlayerList()));
            matchStats.setTeam2PlayerStats(createPlayerStatsList(inningContext.getBattingPlayerList()));
        }
        matchStats.setWinnerTeamName(getWinnerTeamName(matchContext));
        matchStatsRepo.save(matchStats);
        return matchStats;
    }


    public MatchStats findMatchStatsById(String id) throws Exception {
        Optional<MatchStats> matchStats=matchStatsRepo.findById(id);
        if (matchStats.isPresent()) {
            return matchStats.get(); // TODO: 16/03/23  make 1 repo call-done
        } else {
            throw new Exception("MatchStats id not found!");
        }
    }

}
